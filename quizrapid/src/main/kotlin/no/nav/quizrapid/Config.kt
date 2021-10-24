package no.nav.quizrapid

import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import java.util.*

class Config(
    val appName: String,
    val bootstrapServers: List<String>,
    val quizTopic: String,
    private val kafkaTrustStorePath: String?,
    private val kafkaKeyStorePath: String?,
    private val credStorePassword: String?
) {

    companion object {
        fun fromEnv(): Config {
            return Config(
                System.getenv("NAIS_APP_NAME"),
                System.getenv("BOOTSTRAP_SERVERS").split(";"),
                System.getenv("QUIZ_TOPIC") ?: "quiz-rapid",
                System.getenv("KAFKA_TRUSTSTORE_PATH"),
                System.getenv("KAFKA_KEYSTORE_PATH"),
                System.getenv("KAFKA_CREDSTORE_PASSWORD")
            )
        }

        fun local(appName: String, bootstrapServers: List<String>, quizTopic: String,) =
            Config(appName, bootstrapServers, quizTopic,null, null, null)
    }

    internal fun consumerConfig(clientId: String) = Properties().apply {
        put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-$appName")
        put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer-$appName-$clientId")
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
        put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1")
        //put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "$maxPollIntervalMs")
        if (kafkaKeyStorePath != null) {
            this += sslConfig()
        }
    }

    internal fun producerConfig(clientId: String) = Properties().apply {
        put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
        put(ProducerConfig.CLIENT_ID_CONFIG, "producer-$appName-$clientId")
        put(ProducerConfig.ACKS_CONFIG, "1")
        put(ProducerConfig.LINGER_MS_CONFIG, "0")
        put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, "1")
        if (kafkaKeyStorePath != null) {
            this += sslConfig()
        }
    }

    private fun sslConfig() = Properties().apply {
        put("security.protocol", "SSL")
        put("ssl.truststore.location", kafkaTrustStorePath!!)
        put("ssl.truststore.password", credStorePassword!!)
        put("ssl.keystore.type", "PKCS12")
        put("ssl.keystore.location", kafkaKeyStorePath!!)
        put("ssl.keystore.password", credStorePassword)
        put("ssl.key.password", credStorePassword)
    }
}