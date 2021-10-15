import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.LoggerFactory

fun main() {
    Quizboard().start()
}


class Quizboard {
    private val logger = LoggerFactory.getLogger("Quizboard")

    fun start(){
        embeddedServer(Netty, port = 8081, host = "0.0.0.0") {
            routing {
                get("/") {
                    call.respondText("Hello World!")
                    logger.info("Received connection")
                }
            }
        }.start(wait = true)
    }
}


