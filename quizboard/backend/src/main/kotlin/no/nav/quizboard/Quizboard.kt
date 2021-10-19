package no.nav.quizboard

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
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



fun main() {
    val quizboard = Quizboard()
    val logger = LoggerFactory.getLogger("Quizboard")
    RapidServer(Config.fromEnv(), ktorServer(quizboard), quizboard) { records ->
        records.forEach {
            logger.info("message received: ${it.value()}") // Her må vi lese inn Questions og Assessments for boardet
        }
    }.startBlocking()
}

fun ktorServer(quizboard: Quizboard): ApplicationEngine = embeddedServer(CIO, applicationEngineEnvironment {
    connector {
        port = 8081
    }
    module {
        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("static/index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            static("/") {
                resources("static")
            }

            get("/hei") {
                call.respond("QuizBoard")
            }
            get("/board") {
                call.respond(quizboard.quizTeams.toString())
            }
        }
    }

})


