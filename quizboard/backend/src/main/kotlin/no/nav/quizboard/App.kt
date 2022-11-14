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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.broadcast
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import no.nav.quizrapid.Config
import no.nav.quizrapid.RapidServer
import no.nav.quizrapid.objectMapper
import java.util.UUID

val collectorRegistry: CollectorRegistry = CollectorRegistry.defaultRegistry

fun main() {
    val quizboard = Quizboard()
    RapidServer(Config.fromEnv(), ktorServer(quizboard), quizboard).startBlocking()
}

@OptIn(ExperimentalCoroutinesApi::class, ObsoleteCoroutinesApi::class)
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

        val channel = produce {
            while (true) {
                send(SseEvent(data = quizboard.result()))
                delay(2000)
            }
        }.broadcast()

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
                val events = channel.openSubscription()
                try {
                    call.respondSse(events)
                } finally {
                    events.cancel()
                }
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

data class SseEvent<T>(val data: T, val id: String? = UUID.randomUUID().toString())

suspend fun ApplicationCall.respondSse(events: ReceiveChannel<SseEvent<BoardResult>>) {
    response.header(HttpHeaders.AccessControlAllowOrigin, "http://localhost:3000")
    response.cacheControl(CacheControl.NoCache(null))
    respondTextWriter(contentType = ContentType.Text.EventStream) {
        for (event in events) {
            withContext(Dispatchers.IO) {
                event.id?.let {
                    write("id: ${it}\n")
                }
                write("data: ${objectMapper.writeValueAsString(event.data)}\n")
                write("\n")
                flush()
            }
        }
    }
}

