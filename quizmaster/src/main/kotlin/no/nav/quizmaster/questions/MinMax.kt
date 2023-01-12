package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.time.LocalDateTime.now
import kotlin.random.Random

enum class MinMaxType {
    LAVESTE,
    HOYESTE
}

class MinMax(private val frequency: Duration, active: Boolean = false) :
    QuestionCategory("min-max", 10, active) {
    private val numbersList = ArrayList<Int>()
    private var nextQuestion = now() + frequency
    private val fasit = mutableMapOf<String, Int>()

    override fun check(answer: Answer) {
        try {
            fasit[answer.questionId]?.checkAnswer(answer)
        } catch (e: NumberFormatException) {
            logger.warn("Svar = $answer inneholder ugyldig data = ${answer.answer}")
            logger.debug(e.toString())
        }
    }

    private fun Int.checkAnswer(answer: Answer) {
        (this == answer.answer.toInt()).publish(answer.teamName, answer.questionId, answer.messageId)
    }

    override fun newQuestions(): List<Question> {
        return if (now() > nextQuestion && active) listOf(newQuestion()).also { nextQuestion = now() + frequency }
        else emptyList()
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

    override fun sync(question: Question): Boolean {
        val answer = solver(question)
        storeQuestion(question, answer)
        return true
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

        if (type === MinMaxType.HOYESTE) {
            fasit = list.maxOrNull()!!
        } else if (type === MinMaxType.LAVESTE) {
            fasit = list.minOrNull()!!
        }
        return fasit
    }

    private fun solver(question: Question): Int {
        val questionString = question.question
        val minmaxValue = questionString.split(' ')[0]

        val stringList = questionString.split("i")[1].replace(" ", "").replace("[", "").replace("]", "").split(",")
        val intList = stringList.map { it.toInt() }

        return if (minmaxValue == "LAVESTE") {
            intList.minOf { it }
        } else {
            intList.maxOf { it }
        }
    }

}
