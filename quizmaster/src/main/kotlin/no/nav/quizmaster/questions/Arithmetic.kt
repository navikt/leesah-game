package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.time.LocalDateTime.now
import kotlin.random.Random

class Arithmetic(private val frequency: Duration, active: Boolean = false): QuestionCategory("arithmetic", 10, active) {

    private val operators = listOf(Operator.ADD, Operator.MULTI, Operator.SUB, Operator.DIV)
    private var nextQuestion = now() + frequency
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

    // Update fasit with any arithmetic questions published by another Quizmaster
    override fun sync(question: Question): Boolean {
        val answer = arithmeticSolver(question)
        if (answer != null) {
            storeQuestion(question, answer)
            return true
        }
        return false
    }

    private fun generateExpression(): Pair<String, Int> {
        val first = Random.nextInt(101)
        val last = Random.nextInt(101)
        val op = operators[Random.nextInt(operators.size)]
        val exp = "$first ${op.symbol} $last"
        val fasit = op.operation(first, last)
        return Pair(exp, fasit)
    }

    private enum class Operator(val symbol: String, val operation: (Int, Int) -> Int) {
        ADD("+", {first, last -> first + last}),
        MULTI("*", {first, last -> first * last}),
        SUB("-", {first, last -> first - last}),
        DIV("/", {first, last -> first / last}),
    }

    private fun arithmeticSolver(question: Question): Int? {
        val operatorMap = mapOf<String, (Int, Int) -> Int>(
            "+" to { first, last -> first + last },
            "*" to { first, last -> first * last },
            "-" to { first, last -> first - last },
            "/" to { first, last -> first / last },
        )
        val exp = question.question.split(" ")
        return operatorMap[exp[1]]?.invoke(exp[0].toInt(), exp[2].toInt())
    }
}