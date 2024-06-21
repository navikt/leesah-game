package no.nav.quizmaster

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.jackson.*
import io.ktor.metrics.micrometer.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.micrometer.core.instrument.Clock
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.prometheusmetrics.PrometheusConfig
import io.micrometer.prometheusmetrics.PrometheusMeterRegistry
import io.prometheus.metrics.model.registry.PrometheusRegistry
import no.nav.quizrapid.Config
import no.nav.quizrapid.RapidServer
import no.nav.quizrapid.objectMapper

fun main() {
    val quizMaster = QuizMaster()
    val rapidConfig = Config.fromEnv()
    RapidServer(config = rapidConfig, ktor = ktorServer(quizMaster), participant = quizMaster).startBlocking()
}


fun ktorServer(quizMaster: QuizMaster): ApplicationEngine = embeddedServer(CIO, applicationEngineEnvironment {
    val prometheusRegistry = PrometheusRegistry()

    connector {
        port = 8080
    }
    module {
        install(MicrometerMetrics) {
            registry = PrometheusMeterRegistry(
                PrometheusConfig.DEFAULT,
                prometheusRegistry,
                Clock.SYSTEM
            )
            meterBinders = listOf(
                ClassLoaderMetrics(),
                JvmMemoryMetrics(),
                JvmGcMetrics(),
                ProcessorMetrics(),
                JvmThreadMetrics(),
                //LogbackMetrics()
            )
        }
        install(ContentNegotiation) { register(ContentType.Application.Json, JacksonConverter(objectMapper)) }


        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("static/index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            static("/") {
                resources("static/")
            }

            get("/categories") {
                call.respond(quizMaster.categories())
            }

            get("/quiz-stats") {
                call.respond(quizMaster.stats())
            }

            get("/categories/{category}") {
                val category = call.parameters["category"] ?: return@get call.respond(
                    HttpStatusCode.BadRequest,
                    "missing a category"
                )
                quizMaster.stats(category)?.also {
                    call.respond(it)
                } ?: call.respond(HttpStatusCode.NotFound, "could not find category = $category")
            }

            put("/categories/{category}") {
                val category = call.parameters["category"] ?: return@put call.respond(
                    HttpStatusCode.BadRequest,
                    "missing a category"
                )
                quizMaster.activate(category)?.also { call.respond(it) } ?: call.respond(
                    HttpStatusCode.NotFound,
                    "could not find category = $category"
                )
            }

            put("/categories/{category}/{answerId}") {
                val category = call.parameters["category"] ?: return@put call.respond(
                    HttpStatusCode.BadRequest,
                    "missing a category"
                )
                val answerId = call.parameters["answerId"] ?: return@put call.respond(
                    HttpStatusCode.BadRequest,
                    "missing a team"
                )
                quizMaster.accept(category, answerId)?.also { call.respond(it) } ?: call.respond(
                    HttpStatusCode.NotFound,
                    "could not find category = $category containing answerId = $answerId"
                )
            }

            get("/metrics") {
                call.respond(prometheusRegistry.scrape())
            }

            get("/ready") {
                call.respondText("OK")
            }
            get("/alive") {
                call.respondText("OK")
            }
        }
    }
})
