import kotlinx.coroutines.runBlocking
import no.nav.common.KafkaEnvironment
import no.nav.quizmaster.QuizRapid
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.config.SaslConfigs
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class QuizRapidTest {


    private val testTopic = "testTopic"
    private lateinit var kafkaProducer: Producer<String, String>
    private lateinit var kafkaConsumer: Consumer<String, String>
    private val embeddedKafkaEnvironment = KafkaEnvironment(
        autoStart = false,
        noOfBrokers = 1,
        topicInfos = listOf(testTopic).map { KafkaEnvironment.TopicInfo(it, partitions = 1) },
        withSchemaRegistry = false,
        withSecurity = false
    )

    @BeforeAll
    internal fun setup() {
        embeddedKafkaEnvironment.start()
        kafkaProducer = KafkaProducer(producerProperties(), StringSerializer(), StringSerializer())
        kafkaConsumer = KafkaConsumer(consumerProperties(), StringDeserializer(), StringDeserializer())
        kafkaConsumer.subscribe(listOf(testTopic))
    }

    @AfterAll
    internal fun teardown() {
        kafkaConsumer.close()
        kafkaProducer.close()
        embeddedKafkaEnvironment.tearDown()
    }


    @Test
    fun `publish a quiz message`() {

        var counter = 0

        val quizRapid = QuizRapid(listOf(embeddedKafkaEnvironment.brokersURL), rapidTopic = testTopic) {
            println(it.records(testTopic))
            counter += 1
        }
        embeddedKafkaEnvironment.produceToTopic(testTopic, listOf("Hello1", "Hello2", "Hello3"))

        await("wait until recods are sent")
            .atMost(20, TimeUnit.SECONDS)
            .until {
                counter > 0
            }
        assertEquals(3, counter)

    }

    private fun KafkaEnvironment.produceToTopic(name: String, records: List<String>) {
        val clientProperties = producerProperties()
        clientProperties[ProducerConfig.BATCH_SIZE_CONFIG] = 0
        clientProperties[ProducerConfig.LINGER_MS_CONFIG] = 0

        val producer = KafkaProducer(clientProperties, StringSerializer(), StringSerializer())
        records.forEach { r ->
            producer.send(ProducerRecord(name, r, r))
            producer.flush()
        }
    }

    private fun KafkaEnvironment.records(topicName: String): ConsumerRecords<String, String>? {
        KafkaConsumer(consumerProperties(), StringDeserializer(), StringDeserializer())
            .use { consumer ->
                consumer.subscribe(listOf(topicName))
                return consumer.poll(Duration.of(10, ChronoUnit.SECONDS))
            }
    }

    private fun producerProperties() =
        Properties().apply {
            put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaEnvironment.brokersURL)
        }

    private fun consumerProperties(): MutableMap<String, Any> {
        return HashMap<String, Any>().apply {
            put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, embeddedKafkaEnvironment.brokersURL)
            put(ConsumerConfig.GROUP_ID_CONFIG, "integration-test" + UUID.randomUUID().toString().slice(1..5))
            put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        }
    }
}