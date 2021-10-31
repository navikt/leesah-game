package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration

class PingPong(maxCount: Int = 1, active: Boolean = true, interval: Duration) :
    QuestionCategory("ping-pong", maxCount, active, interval) {

    private val publishedQuestions = mutableSetOf<String>()

    override fun check(answer: Answer) {
        (answer.questionId in publishedQuestions && answer.answer.lowercase() == "pong")
            .publish(answer.teamName, answer.questionId, answer.messageId)
    }

    override fun newQuestions(): List<Question> {
        if(!active) return emptyList()
        val newQuestion = Question(category = category, question = "ping")
        storeQuestion(newQuestion)
        return listOf(newQuestion)
    }

    private fun storeQuestion(newQuestion: Question) {
        publishedQuestions.add(newQuestion.messageId)
    }
}