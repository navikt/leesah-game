package no.nav.quizmaster

import io.ktor.application.*
import io.ktor.http.*
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
import org.slf4j.LoggerFactory

fun main() {
    QuizmasterServer(Config.fromEnv()).start()
}

class QuizmasterServer(private val config: Config) {

    private val log = LoggerFactory.getLogger("Quizmaster")

    fun start() {
        val quizRapid = QuizRapid(
            config.bootstrapServers,
            onRecords = { consumerRecords ->
                consumerRecords.records("quiz-rapid").forEach {
                    println(it.value())

                    // TODO just an example
                    if (it.value().startsWith("register ")) {
                        publish("Registration received for team: ${it.value().split(" ")[1]}")
                    }
                }
            }
        )
        val ktorServer = ktorServer().start(false)
        try {
            quizRapid.start()
        } finally {
            val gracePeriod = 5000L
            val forcefulShutdownTimeout = 30000L
            log.info("shutting down ktor, waiting $gracePeriod ms for workers to exit. Forcing shutdown after $forcefulShutdownTimeout ms")
            ktorServer.stop(gracePeriod, forcefulShutdownTimeout)
            log.info("ktor shutdown complete: end of life. goodbye.")
        }

    }
}


fun ktorServer(): ApplicationEngine = embeddedServer(CIO, applicationEngineEnvironment {
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
                //LogbackMetrics()
            )
        }
        routing {
            get("/") {
                call.respond("Quizmaster")
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
