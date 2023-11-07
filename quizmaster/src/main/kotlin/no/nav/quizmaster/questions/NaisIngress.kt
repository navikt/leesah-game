package no.nav.quizmaster.questions

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking
import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question

class NaisIngress(maxCount: Int = 1, active: Boolean = true): QuestionCategory("nais-ingress", maxCount, active) {
    override fun check(answer: Answer) {
        var statusCode: Int
        val hostUrl = answer.answer
        val security = hostUrl.split(":").first()
        if (!answer.answer.contains(answer.teamName)
            || !(("\\.intern\\.dev\\.nav\\.no\\/?$").toRegex().containsMatchIn(answer.answer))
            || security == "https" ) {
            false.publish(answer.teamName, answer.questionId, answer.messageId)
        }

        runBlocking {
            try {
                logger.info("henter response")
                val response: HttpResponse = HttpClient().use {
                    logger.info("http clienten: $it")
                    it.get(hostUrl)
                }
                statusCode = response.status.value
                (statusCode == 200).publish(answer.teamName, answer.questionId, answer.messageId)
            } catch (e: Exception){
                logger.info("kaster exception")
            }
        }
    }

    override fun newQuestions(): List<Question> {
        if(!active) return emptyList()
        val newQuestion = Question(category = category, question = "[NAIS Oppgave] Lag en NAIS ingress for appen din i formatet: <app navn>.intern.dev.nav.no. Send oss din nye ingress som svar, vi forventer at den svarer med 200 ok.")
        return listOf(newQuestion)
    }

    override fun sync(question: Question) = true


}