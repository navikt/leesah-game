package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration

class PingPong(maxCount: Int = 1, active: Boolean = true, interval: Duration) :
    QuestionCategory("ping-pong", maxCount, active, interval) {

    override fun check(answer: Answer) {
        (answer.questionId in sentQuestions.map { it.id() } && answer.answer.lowercase() == "pong")
            .publish(answer.teamName, answer.questionId, answer.messageId)
    }

    override fun newQuestions(): List<Question> {
        if(!active) return emptyList()
        val newQuestion = Question(category = category, question = "ping")
        return listOf(newQuestion)
    }
}