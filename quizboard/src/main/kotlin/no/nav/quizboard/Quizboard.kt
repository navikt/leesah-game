package no.nav.quizboard

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.prometheus.client.CollectorRegistry
import no.nav.quizrapid.Config
import no.nav.quizrapid.RapidServer
import org.slf4j.LoggerFactory

fun main() {
    val logger = LoggerFactory.getLogger("Quizboard")
    RapidServer(Config.fromEnv(), ktorServer()) { records ->
        records.forEach {
            logger.info("message received: ${it.value()}") // Her må vi lese inn Questions og Assessments for boardet
        }
    }.startBlocking()
}


fun ktorServer(): ApplicationEngine = embeddedServer(CIO, applicationEngineEnvironment {
    connector {
        port = 8081
    }
    module {
        routing {
            get("/") {
                call.respond("QuizBoard")
            }
        }
    }
})


