package no.nav.quizboard

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
import io.micrometer.prometheus.PrometheusConfig
import io.micrometer.prometheus.PrometheusMeterRegistry
import io.prometheus.client.CollectorRegistry
import io.prometheus.client.exporter.common.TextFormat
import no.nav.quizrapid.Config
import no.nav.quizrapid.RapidServer
import no.nav.quizrapid.objectMapper

val collectorRegistry: CollectorRegistry = CollectorRegistry.defaultRegistry

fun main() {
    val quizboard = Quizboard()
    RapidServer(Config.fromEnv(), ktorServer(quizboard), quizboard).startBlocking()
}

fun ktorServer(quizboard: Quizboard): ApplicationEngine = embeddedServer(CIO, applicationEngineEnvironment {

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
                resources("static")
            }

            get("/alive") {
                call.respond("OK")
            }
            get("/ready") {
                call.respond("OK")
            }

            get("/board") {
                call.respond(quizboard.result())
            }

            get("/metrics") {
                val names = call.request.queryParameters.getAll("name[]")?.toSet() ?: emptySet()

                call.respondTextWriter(ContentType.parse(TextFormat.CONTENT_TYPE_004)) {
                    TextFormat.write004(this, collectorRegistry.filteredMetricFamilySamples(names))
                }
            }
        }
    }

})


