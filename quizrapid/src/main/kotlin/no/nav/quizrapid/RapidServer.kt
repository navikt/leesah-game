package no.nav.quizrapid

import io.ktor.server.engine.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.slf4j.LoggerFactory


class RapidServer(config: Config, private val ktor: ApplicationEngine, participant: QuizParticipant = nooParticipant, run: QuizRapid.(records: ConsumerRecords<String, String>) -> Unit) {

    private val log = LoggerFactory.getLogger(config.appName)
    private val quizRapid = QuizRapid(
        config.appName,
        config.bootstrapServers,
        rapidTopic = config.quizTopic,
        participant = participant,
        run = run
    )

    fun startBlocking() {
        runBlocking { start() }
    }

    suspend fun start() {
        val ktorServer = ktor.start(false)
        try {
            coroutineScope {
                launch { quizRapid.start() }
            }

        } finally {
            val gracePeriod = 5000L
            val forcefulShutdownTimeout = 30000L
            log.info("shutting down ktor, waiting $gracePeriod ms for workers to exit. Forcing shutdown after $forcefulShutdownTimeout ms")
            ktorServer.stop(gracePeriod, forcefulShutdownTimeout)
            log.info("ktor shutdown complete: end of life. goodbye.")
        }

    }
}