package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.time.LocalDateTime

class Deduplication(
    private val interval: Duration,
    maxCount: Int,
    active: Boolean
) : QuestionCategory("deduplication", maxCount, active) {

    private var nextQuestion = LocalDateTime.now()
    private val teamAnswers = mutableMapOf<String, List<String>>()
    private val completed = mutableListOf<String>()
    private var question: Question = Question(category = category, question = "answer this question only once with an <you wont dupe me!>")
    private val resetAnswer = "you duped me!"
    private val fasit = "you wont dupe me!"

    override fun check(answer: Answer) {
        if(answer.questionId != question.messageId) return
        if(answer.answer == resetAnswer) {
            teamAnswers[answer.teamName] = emptyList()
            return
        }
        if(answer.answer != fasit) {
            false.publish(answer.teamName, question.messageId, answer.messageId)
        }
        teamAnswers.compute(answer.teamName) { key, value, -> if(value == null)  listOf(answer.messageId) else value + answer.messageId }
        if (teamAnswers[answer.teamName]!!.size > 1) {
            false.publish(answer.teamName, question.messageId, answer.messageId)
        }

    }

    override fun newQuestions(): List<Question> {
        if(maxCountReached()) {
            publishAssessments()
        }

        return if (LocalDateTime.now() > nextQuestion && active) listOf(question).also {
            nextQuestion = LocalDateTime.now() + interval
        }
        else emptyList()
    }

    private fun publishAssessments() {
        teamAnswers.filter { it.value.size == 1 && it.key !in completed }.forEach {
            true.publish(it.key, question.messageId, it.value[0])
            completed.add(it.key)
        }
    }
}