package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question

class NaisIngress(maxCount: Int = 1, active: Boolean = true): QuestionCategory("nais-ingress", maxCount, active) {
    override fun check(answer: Answer) {
        (("\\.intern\\.dev\\.nav\\.no\\/?$").toRegex().containsMatchIn(answer.answer)).publish(answer.teamName, answer.questionId, answer.messageId)
    }

    override fun newQuestions(): List<Question> {
        if(!active) return emptyList()
        val newQuestion = Question(category = category, question = "[NAIS Oppgave] Lag en NAIS ingress for appen din i formatet: <app navn>.intern.dev.nav.no. Send oss din nye ingress som svar, vi forventer at den svarer med 200 ok.")
        return listOf(newQuestion)
    }

    override fun sync(question: Question) = true


}