package no.nav.quizmaster.questions

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question

class NaisServiceDiscovery(maxCount: Int = 1, active: Boolean = true): QuestionCategory("nais-service-discovery", maxCount, active) {
    override fun check(answer: Answer) {
/*
        val client = HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 2000
            }
        }

 */
        var statusCode: Int
        val hostUrl = "http://${answer.answer}"

        runBlocking {
            try {
                val response: HttpResponse = HttpClient().use {
                    it.get(hostUrl)
                }
                statusCode = response.status.value
                (statusCode == 200).publish(answer.teamName, answer.questionId, answer.messageId)
            } catch (e: Exception){
                throw e
            }
        }
    }

    override fun newQuestions(): List<Question> {
        if(!active) return emptyList()
        val newQuestion = Question(category = category, question = "[NAIS Oppgave] Quizmaster ønsker å snakke med appen din via noe som kalles for Service Discovery, men den blir stoppet av access policy hos deg. Gi quizmaster tilgang og svar med appnavnet ditt. Quizmaster forventer at appen din svarer med 200 ok.")
        return listOf(newQuestion)
    }

    override fun sync(question: Question) = true


}