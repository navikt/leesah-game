package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.time.LocalDateTime
import kotlin.random.Random
import kotlin.streams.toList

enum class MinMaxType {
    LAVESTE,
    HOYESTE
}

class MinMaxInt(private val frequency: Duration, active: Boolean = false):
    QuestionCategory("min-max-int", 10, active) {
    private val numbersList = ArrayList<Int>()
    private var nextQuestion = LocalDateTime.now() + frequency
    private val fasit = mutableMapOf<String, Int>()

    override fun check(answer: Answer) {
        try {
            fasit[answer.questionId]?.checkAnswer(answer)
        } catch (e: NumberFormatException ) {
            logger.warn("answer = $answer contains invalid data = ${answer.answer}")
            logger.debug(e.toString())
        }
    }

    private fun Int.checkAnswer(answer: Answer) {
        (this == answer.answer.toInt()).publish(answer.teamName, answer.questionId, answer.messageId)
    }

    override fun newQuestions(): List<Question> {
        return if (LocalDateTime.now() > nextQuestion && active) listOf(newQuestion()).also { nextQuestion = LocalDateTime.now() + frequency }
        else emptyList()
    }

    override fun sync(question: Question): Boolean {
        val split = question.question.split(' ')

        val answer = solution(MinMaxType.valueOf(split[0]), split[2].chars().toList())
        if (answer != null) {
            storeQuestion(question, answer)
            return true
        }
        return false
    }

    private fun newQuestion(): Question {
        val (exp, fasit) = generateExpression()
        val newQuestion = Question(category = category, question = exp)
        storeQuestion(newQuestion, fasit)
        return newQuestion
    }

    private fun storeQuestion(newQuestion: Question, fasit: Int) {
        this.fasit[newQuestion.messageId] = fasit
    }

    private fun generateExpression(): Pair<String, Int> {
        numbersList.clear()
        val randomType = MinMaxType.values().toList().shuffled().first()

        for (i in 1..20) {
            numbersList.add(Random.nextInt(101))
        }
        val exp = "$randomType i $numbersList"
        val fasit = solution(randomType, numbersList);
        return (Pair(exp, fasit))
    }

    private fun solution(type: MinMaxType, list: List<Int>): Int {
        var fasit = 0;

        if(type === MinMaxType.HOYESTE) {
            fasit = list.maxOrNull()!!
        }
        else if(type === MinMaxType.LAVESTE) {
            fasit = list.minOrNull()!!
        }
        return fasit
    }

}
