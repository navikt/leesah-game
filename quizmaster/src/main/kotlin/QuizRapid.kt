package no.nav.quizmaster

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.errors.*
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.slf4j.LoggerFactory
import java.time.Duration
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class QuizRapid(
    private val bootrapServers: List<String>,
    private val clientId: String = UUID.randomUUID().toString().slice(1..5),
    private val rapidTopic: String = "quiz-rapid",
    private val onRecords: QuizRapid.(records: ConsumerRecords<String, String>) -> Unit
) {
    private val consumer = KafkaConsumer(consumerConfig(), StringDeserializer(), StringDeserializer())
    private val producer = KafkaProducer(producerConfig(), StringSerializer(), StringSerializer())
    private val log = LoggerFactory.getLogger(QuizRapid::class.java)

    private val running = AtomicBoolean(false)

    internal fun isRunning() = running.get()

    internal fun publish(message: String) {
        //check(!producerClosed.get()) { "can't publish messages when producer is closed" }
        producer.send(ProducerRecord(rapidTopic, message)) { _, err ->
            if (err == null || !isFatalError(err)) return@send
            log.error("Shutting down QuizRapid due to fatal error: ${err.message}", err)
            stop()
        }
        producer.flush()
    }

    fun start() {
        log.info("starting QuizRapid")
        if (true == running.getAndSet(true)) return log.info("QuizRapid already started")
        consumeMessages()
    }

    fun stop() {
        log.info("stopping QuizRapid")
        if (!running.getAndSet(false)) return log.info("rapid already stopped")
        consumer.wakeup()
    }

    private fun consumeMessages() {
        var lastException: Exception? = null
        try {
            consumer.subscribe(listOf(rapidTopic))
            while (running.get()) {
                consumer.poll(Duration.ofSeconds(1)).also {
                    onRecords(it)
                }
            }
        } catch (err: WakeupException) {
            // throw exception if we have not been told to stop
            if (running.get()) throw err
        } catch (err: Exception) {
            lastException = err
            throw err
        } finally {
            closeResources(lastException)
        }
    }

    private fun closeResources(lastException: Exception?) {
        if (running.getAndSet(false)) {
            log.warn("stopped consuming messages due to an error", lastException)
        } else {
            log.info("stopped consuming messages after receiving stop signal")
        }
        tryAndLog(producer::close)
        tryAndLog(consumer::unsubscribe)
        tryAndLog(consumer::close)
    }

    private fun tryAndLog(block: () -> Unit) {
        try {
            block()
        } catch (err: Exception) {
            log.error(err.message, err)
        }
    }

    private fun consumerConfig() = Properties().apply {
        put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootrapServers)
        put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-quizmaster")
        put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer-quizmaster-$clientId")
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
        put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
        //put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "$maxPollIntervalMs")
    }

    private fun producerConfig() = Properties().apply {
        put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootrapServers)
        put(ProducerConfig.CLIENT_ID_CONFIG, "producer-quizmaster-$clientId")
        put(ProducerConfig.ACKS_CONFIG, "1")
        put(ProducerConfig.LINGER_MS_CONFIG, "0")
        put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "1")
    }

    companion object {
        private fun isFatalError(err: Exception) = when (err) {
            is InvalidTopicException,
            is RecordBatchTooLargeException,
            is RecordTooLargeException,
            is UnknownServerException,
            is AuthorizationException -> true
            else -> false
        }
    }

}






