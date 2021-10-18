package no.nav.quizboard

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.prometheus.client.CollectorRegistry
import no.nav.quizrapid.*
import org.slf4j.LoggerFactory


class Quizboard: QuizParticipant {

    internal val quizTeams = mutableMapOf<String, Int>()

    override fun handle(question: Question) = true

    override fun handle(answer: Answer) = true

    override fun handle(assessment: Assessment): Boolean {
        if( assessment.ok()) quizTeams.merge(assessment.teamName, 1, Int::plus)
        return true
    }

    override fun messages(): List<Message> = emptyList()

}
val quizboard = Quizboard()



fun main() {
    val logger = LoggerFactory.getLogger("Quizboard")
    RapidServer(Config.fromEnv(), ktorServer(), quizboard) { records ->
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
            get("/board") {
                call.respond(quizboard.quizTeams.toString())
            }
        }
    }
})


