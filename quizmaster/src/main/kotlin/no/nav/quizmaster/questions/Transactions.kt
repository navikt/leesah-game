package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.util.*
import kotlin.Exception

enum class TransactionType {
    INNSKUDD,
    UTTREKK
}

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val type: TransactionType,
    val amount: Int
) {
    override fun toString(): String = "${type.name} $amount"

    fun sum(balance: Int): Int {
        return when (this.type) {
            TransactionType.INNSKUDD ->
                balance.plus(this.amount)

            TransactionType.UTTREKK ->
                balance.minus(this.amount)
        }
    }
}


class Transactions(maxCount: Int = 20, active: Boolean = false, interval: Duration = Duration.ZERO) :
    QuestionCategory("transactions", maxCount, active, interval) {

    data class Solution(val questionId: String, val correctAnswer: Int)
    private val solutions = mutableListOf<Solution>()

    override fun check(answer: Answer) {
        val solution = solutions.firstOrNull { it.questionId == answer.questionId }
        if (solution == null) {
            logger.warn("answer = $answer does not refer to a stored question = ${answer.questionId}")
            return
        }
        val isCorrect = try {
                solution.correctAnswer == answer.answer.toInt()
            } catch (e: Exception) {
                logger.info("${answer.answer} with answerId ${answer.messageId} and questionId ${answer.questionId} throws error", e)
                false
        }

        if (!isCorrect) {
            logger.info("${answer.answer} with answerId ${answer.messageId} and questionId ${answer.questionId} is incorrect")
        }
        isCorrect.publish(answer.teamName, questionId = solution.questionId, answerId = answer.id())

    }

    companion object {
        fun calculateBalance(transactions: List<Transaction>): Int {
            var balance = 0
            transactions.forEach { transaction ->
                balance = when (transaction.type) {
                    TransactionType.INNSKUDD ->
                        balance.plus(transaction.amount)

                    TransactionType.UTTREKK ->
                        balance.minus(transaction.amount)
                }
            }
            return balance
        }
    }

    override fun newQuestions(): List<Question> {
        val transaction = randomTransaction()
        val question = Question(
            category = category,
            question = transaction.toString()
        )
        storeSolution(question, transaction)
        return listOf(question)
    }

    private fun storeSolution(question: Question, transaction: Transaction) {
        val balance = solutions.lastOrNull()?.correctAnswer ?: 0
        solutions.add(Solution(question.id(), transaction.sum(balance)))
    }

    override fun sync(question: Question): Boolean {
        return try {
            val transaction = question.toTransaction()
            storeSolution(question, transaction)
            true
        } catch (e: Exception) {
            logger.error("Ignoring question due to error syncing: ${question.json()} to Transactions error: ${e.localizedMessage}", e)
            false
        }
    }

    private fun randomTransaction(): Transaction = Transaction(
        type = TransactionType.values().random(),
        amount = (10..10000).random()
    )

    private fun Question.toTransaction() : Transaction {
        val type = this.question.split(" ")[0]
        val amount = this.question.split(" ")[1].toInt()
        return Transaction(type = TransactionType.valueOf(type), amount = amount)
    }

}