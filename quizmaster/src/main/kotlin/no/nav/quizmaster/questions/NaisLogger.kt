package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question

class NaisLogger(maxCount: Int = 1, active: Boolean = true): QuestionCategory("nais-log", maxCount, active) {
    override fun check(answer: Answer) {
        (answer.answer == answer.teamName).publish(answer.teamName, answer.questionId, answer.messageId)
    }

    override fun newQuestions(): List<Question> {
        if(!active) return emptyList()
        val newQuestion = Question(category = category, question = "[NAIS Oppgave] Quizmaster har logget en hemmelig nøkkel for deg i sine logger, klarer du å finne denne nøkkelen og sende den tilbake?")

        return listOf(newQuestion)
    }

    override fun sync(question: Question) = true


}