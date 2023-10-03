package no.nav.quizmaster


import io.ktor.http.*
import io.ktor.http.ContentType.Application
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.metrics.micrometer.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.micrometer.core.instrument.Clock
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics
import io.micrometer.core.instrument.binder.system.ProcessorMetrics
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.common.TextFormat
import no.nav.quizrapid.Config
import no.nav.quizrapid.RapidServer
import no.nav.quizrapid.objectMapper

fun main() {
    val quizMaster = QuizMaster()
    val rapidConfig = Config.fromEnv()
    RapidServer(config = rapidConfig, ktor = ktorServer(quizMaster), participant = quizMaster).startBlocking()
}


fun ktorServer(quizMaster: QuizMaster): ApplicationEngine = embeddedServer(CIO, applicationEngineEnvironment {
    val collectorRegistry = CollectorRegistry.defaultRegistry

    connector {
        port = 8080
    }
    module {
        install(MicrometerMetrics) {
            registry = PrometheusMeterRegistry(
                PrometheusConfig.DEFAULT,
                collectorRegistry,
                Clock.SYSTEM
            )
            meterBinders = listOf(
                ClassLoaderMetrics(),
                JvmMemoryMetrics(),
                JvmGcMetrics(),
                ProcessorMetrics(),
                JvmThreadMetrics(),
            )
        }
        install(ContentNegotiation) { register(Application.Json, JacksonConverter(objectMapper)) }


        routing {
            get("/") {
                call.respondText(
                    this::class.java.classLoader.getResource("static/index.html")!!.readText(),
                    ContentType.Text.Html
                )
            }
            staticResources("/", "static/")

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
                val names = call.request.queryParameters.getAll("name[]")?.toSet() ?: emptySet()

                call.respondTextWriter(ContentType.parse(TextFormat.CONTENT_TYPE_004)) {
                    TextFormat.write004(this, collectorRegistry.filteredMetricFamilySamples(names))
                }
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
