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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import no.nav.quizmaster.no.nav.quizmaster.Config
import no.nav.quizmaster.no.nav.quizmaster.QuizMaster
import no.nav.quizmaster.no.nav.quizmaster.QuizRapid
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicBoolean

fun main() {
    QuizmasterServer(Config.fromEnv()).startBlocking()
}

class QuizmasterServer(private val config: Config) {

    private val log = LoggerFactory.getLogger("Quizmaster")
    private val quizMaster = QuizMaster()
    private val quizRapid = QuizRapid(
        config.bootstrapServers,
        rapidTopic = config.quizTopic
    ) { consumerRecords ->
        consumerRecords.records(config.quizTopic).forEach { record ->
            log.debug("Message received on rapid", record.value())
            quizMaster.handle(record.value())
        }
        quizMaster.events().forEach {
            log.debug("publishing event", it)
            publish(it)
        }
    }

    fun startBlocking() {
        runBlocking { start() }
    }

    suspend fun start() {
        val ktorServer = ktorServer().start(false)
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
