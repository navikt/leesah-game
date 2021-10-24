package no.nav.quizboard

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import no.nav.quizrapid.*
import org.slf4j.LoggerFactory



fun main() {
    val quizboard = Quizboard()
    RapidServer(Config.fromEnv(), ktorServer(quizboard), quizboard).startBlocking()
}

fun ktorServer(quizboard: Quizboard): ApplicationEngine = embeddedServer(CIO, applicationEngineEnvironment {
    connector {
        port = 8081
    }
    module {
        install(ContentNegotiation) { register(ContentType.Application.Json, JacksonConverter(objectMapper)) }


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
                call.respond(quizboard.result())
            }
        }
    }

})


