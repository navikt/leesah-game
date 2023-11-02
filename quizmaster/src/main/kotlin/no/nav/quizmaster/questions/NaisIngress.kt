package no.nav.quizmaster.questions

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question

class NaisIngress(maxCount: Int = 1, active: Boolean = true): QuestionCategory("nais-ingress", maxCount, active) {
    override fun check(answer: Answer) {
        var ingress = answer.answer
        if (!ingress.startsWith("https://")) {
            ingress = "https://$ingress"
        }
        if (ingress.matches(("\\.intern\\.dev\\.nav\\.no\\/?$").toRegex())) {
            false.publish(answer.teamName, answer.questionId, answer.messageId)
            return
        }
        val client = HttpClient(CIO) {
            install(HttpTimeout) {
                requestTimeoutMillis = 5000
            }
        }
        var statusCode: Int

        runBlocking {
            val response: HttpResponse = client.get("https://ktor.io/docs/welcome.html")
            statusCode = response.status.value
        }

        (statusCode == 200).publish(answer.teamName, answer.questionId, answer.messageId)

    }

    override fun newQuestions(): List<Question> {
        if(!active) return emptyList()
        val newQuestion = Question(category = category, question = "[NAIS Oppgave] Lag en NAIS ingress for appen din i formatet: <app navn>.intern.dev.nav.no. Send oss din nye ingress som svar, vi forventer at den svarer med 200 ok.")
        return listOf(newQuestion)
    }

    override fun sync(question: Question) = true


}