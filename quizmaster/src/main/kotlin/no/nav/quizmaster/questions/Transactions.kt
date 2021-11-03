package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

enum class TransactionType{
    INNSKUDD,
    UTTREKK
}

data class Transaction(
    val id : String = UUID.randomUUID().toString(),
    val type : TransactionType,
    val amount : Int
) {
    override fun toString(): String = "${type.name} $amount"
}


class Transactions(maxCount : Int = 20, active: Boolean = false, interval : Duration = Duration.ZERO):
    QuestionCategory("transactions", maxCount, active, interval) {

    private var nextQuestion = LocalDateTime.now()
    private val publishedQuestions = mutableListOf<Pair<String, Transaction>>()

    override fun check(answer: Answer) {
        val question : Pair<String, Transaction>? = publishedQuestions.find {
            it.first == answer.questionId
        }
        if (question == null){
            logger.warn("answer = $answer does not refer to a stored question = ${answer.answer}")
        } else {
            val transactionsUpToAnswer = publishedQuestions
                .subList(0, publishedQuestions.indexOf(question) + 1)
                .map { it.second }
            val isAnswerCorrect = calculateBalance(transactionsUpToAnswer) == answer.answer.toInt()
            if (!isAnswerCorrect){
                logger.info("${answer.answer} with answerId ${answer.messageId} and questionId ${answer.questionId} is incorrect")
            }
            isAnswerCorrect.publish(answer.teamName, questionId = question.first, answerId = answer.messageId)
        }
    }

    companion object {
        fun calculateBalance(transactions: List<Transaction>): Int {
            var balance = 0;
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

    private fun storeQuestion(newQuestion: Question, transaction : Transaction) {
        publishedQuestions.add(Pair(newQuestion.messageId, transaction))
    }

    override fun newQuestions(): List<Question> {
        if (!(LocalDateTime.now() > nextQuestion && active)) {
            return emptyList()
        }

        val transactions = getTransactions()
        return transactions.map { transaction ->
            Question(
                category = category,
                question = transaction.toString()
            ).also {
                storeQuestion(it, transaction)
            }
        }
    }

    private fun randomTransaction() : Transaction = Transaction(
        type = TransactionType.values().random(),
        amount = (10..10000).random()
    )

    private fun getTransactions() : List<Transaction> {
        val transactions = mutableListOf<Transaction>()
        repeat(maxCount){
            transactions.add(randomTransaction())
        }
        return transactions
    }
}