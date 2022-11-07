package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration

class PendingQuestion(category: String, active: Boolean = false, private val question: String) :
    QuestionCategory(category, 1, active, Duration.ZERO) {
    private val pending = mutableMapOf<String, List<Answer>>()
    private val accepted = mutableMapOf<String, List<String>>()


    override fun check(answer: Answer) {
        if (accepted[answer.questionId]?.contains(answer.teamName) != true)
            pending.compute(answer.questionId) { _, value ->
                value?.run {
                    filter { it.teamName != answer.teamName }.plus(answer)
                } ?: listOf(answer)
            }
    }

    override fun accept(answerId: String) {
        pending
            .flatMap { it.value }
            .find { it.id() == answerId }
            ?.let { answer ->
                true.publish(answer.teamName, answer.questionId, answer.id())
                pending[answer.questionId] = pending[answer.questionId]!!.filter { it.id() != answerId }
                accepted.compute(answer.questionId) { _, value ->
                    value?.apply { plus(answer.teamName) } ?: listOf(
                        answer.teamName
                    )
                }
            }
    }

    override fun stats(): CategoryStats {
        return super.stats()
            .copy(pendingAnswers = pending.flatMap { it.value }.map { PendingAnswer(it.teamName, it.id(), it.answer) })
    }

    override fun newQuestions(): List<Question> =
        if (active) listOf(Question(category = category, question = question)) else emptyList()

    override fun sync(question: Question): Boolean {
        pending[question.id()] = emptyList()
        accepted[question.id()] = emptyList()
        return true
    }
}