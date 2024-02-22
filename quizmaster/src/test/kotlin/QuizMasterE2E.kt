import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import no.nav.common.KafkaEnvironment
import no.nav.quizmaster.QuizMaster
import no.nav.quizrapid.*
import org.apache.kafka.clients.CommonClientConfigs
import org.apache.kafka.clients.consumer.Consumer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.Producer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.awaitility.Awaitility
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

private const val TEST_TEAM = "test-team"

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class QuizMasterE2E {

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
    @Disabled
    fun `questions published by an earlier Quizmaster that crashes are assessed by the new Quizmaster`() {
        val config = Config.local("testApp", listOf(embeddedKafkaEnvironment.brokersURL), testTopic)
        val firstQuizMaster = QuizMaster()
        val firstRapid = QuizRapid(config, rapidTopic = testTopic, participant = firstQuizMaster)
        val firstJob = GlobalScope.launch {
            firstRapid.start()
        }
        firstQuizMaster.activate("team-registration")
        firstQuizMaster.activate("ping-pong")

        assertQuestionIsPublished("team-registration", 1)
        assertQuestionIsPublished("ping-pong", 1)
        firstRapid.stop()
        firstJob.cancel()

        val secondRapid = QuizRapid(config, rapidTopic = testTopic, participant = QuizMaster())
        val secondQuizMaster = GlobalScope.launch {
            secondRapid.start()
        }

        Thread.sleep(1000) // Wait for second QuizMaster to start running
        assertQuestionIsPublished("team-registration", 1)
        assertQuestionIsPublished("ping-pong", 1)

        val teamRegistrationQuestion = recordsOfType<Question>(testTopic).first { it.category == "team-registration" }
        val pingPongQuestion = recordsOfType<Question>(testTopic).first { it.category == "ping-pong" }

        assertAssessmentIsPublished("team-registration", sendAnswer(teamRegistrationQuestion, "000000"))
        assertAssessmentIsPublished("ping-pong", sendAnswer(pingPongQuestion, "pong"))

        secondQuizMaster.cancel()
    }

    private fun assertQuestionIsPublished(questionCategory: String, number: Int) {
        Awaitility.await("there should be $number $questionCategory")
            .atMost(10, TimeUnit.SECONDS)
            .until {
                recordsOfType<Question>(testTopic)
                    .filter { it.category == questionCategory }
                    .size.run { this == number }
            }
    }

    private fun assertAssessmentIsPublished(
        questionCategory: String,
        answerId: String,
        status: AssessmentStatus = AssessmentStatus.SUCCESS
    ) {
        Awaitility.await("answer $answerId for $questionCategory should have assessment with $status")
            .atMost(10, TimeUnit.SECONDS)
            .until {
                recordsOfType<Assessment>(testTopic)
                    .filter { it.category == questionCategory }
                    .filter { it.status == status }
                    .any { it.answerId == answerId }
            }
    }

    private fun sendAnswer(question: Question, answer: String): String {
        val answerMessage = Answer(
            category = question.category,
            teamName = TEST_TEAM,
            answer = answer,
            questionId = question.id()
        )
        produceToTopic(
            testTopic,
            listOf(
                answerMessage.json()
            )
        )
        return answerMessage.id()
    }

    private fun produceToTopic(name: String, records: List<String>) {
        val clientProperties = producerProperties()
        clientProperties[ProducerConfig.BATCH_SIZE_CONFIG] = 0
        clientProperties[ProducerConfig.LINGER_MS_CONFIG] = 0

        val producer = KafkaProducer(clientProperties, StringSerializer(), StringSerializer())
        records.forEach { r ->
            producer.send(ProducerRecord(name, r, r))
            producer.flush()
        }
    }

    private inline fun <reified T> recordsOfType(topicName: String): List<T> {
        KafkaConsumer(consumerProperties(), StringDeserializer(), StringDeserializer())
            .use { consumer ->
                consumer.subscribe(listOf(topicName))
                return consumer.poll(Duration.of(10, ChronoUnit.SECONDS))
                    .mapNotNull {
                        try {
                            objectMapper.readValue(it.value())
                        } catch (e: Exception) {
                            null
                        }
                    }
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
