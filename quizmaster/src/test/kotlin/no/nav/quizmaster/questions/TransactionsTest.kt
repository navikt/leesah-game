package no.nav.quizmaster.questions

import no.nav.quizmaster.questions.TransactionType.INNSKUDD
import no.nav.quizmaster.questions.TransactionType.UTTREKK
import no.nav.quizmaster.questions.Transactions.Companion.calculateBalance
import no.nav.quizrapid.Answer
import no.nav.quizrapid.Assessment
import no.nav.quizrapid.AssessmentStatus
import no.nav.quizrapid.Question
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Duration

internal class TransactionsTest {

    @Test
    fun `answering the last question uses all transactions for calculating the balance`() {
        val transactionCategory = Transactions(interval = Duration.ZERO)
        transactionCategory.activate()
        val questions = (0..3).flatMap { transactionCategory.questions() }
        val transactions = questions.map { it.toTransaction() }
        val transactionSum = calculateBalance(transactions)
        val answerToLastMessage = Answer(
            category = "transactions",
            teamName = "testteam",
            questionId = questions.last().messageId,
            answer = transactionSum.toString()
        )
        transactionCategory.check(answerToLastMessage)
        val correctAssessments = transactionCategory.events()
        assertTrue(correctAssessments.size == 1)
        assertTrue((correctAssessments.all { (it as Assessment).isOk() }))
    }

    @Test
    fun `answering the second question uses only the first two transactions to calculate the balance`() {
        val transactionCategory = Transactions(interval = Duration.ZERO, active = true)
        val questions = (0..3).flatMap { transactionCategory.questions() }
        val relevantQuestions = questions.subList(0, 3)
        val transactions = relevantQuestions.map { it.toTransaction() }
        val transactionSum = calculateBalance(transactions)
        val answerToFirstTwoMessages = Answer(
            category = "transactions",
            teamName = "testteam",
            questionId = relevantQuestions.last().messageId,
            answer = transactionSum.toString()
        )
        transactionCategory.check(answerToFirstTwoMessages)
        val correctAssessments = transactionCategory.events()
        assertTrue(correctAssessments.size == 1)
        assertTrue((correctAssessments.all { (it as Assessment).isOk() }))
    }
    @Test
    fun `answering the first question uses only the first two transactions to calculate the balance`() {
        val transactionCategory = Transactions(interval = Duration.ZERO, active = true)
        val questions = (0..3).flatMap { transactionCategory.questions() }
        val relevantQuestions = listOf(questions.first())
        val transactions = relevantQuestions.map { it.toTransaction() }
        val transactionSum = calculateBalance(transactions)
        val answerToCorrectMessage = Answer(
            category = "transactions",
            teamName = "testteam",
            questionId = relevantQuestions.last().messageId,
            answer = transactionSum.toString()
        )
        transactionCategory.check(answerToCorrectMessage)
        val correctAssessments = transactionCategory.events()
        assertTrue(correctAssessments.size == 1)
        assertTrue((correctAssessments.all { (it as Assessment).isOk() }))
    }

    @Test
    fun `answering the all questions but the last with the sum of all transactions is incorrect`() {
        val transactionCategory = Transactions(interval = Duration.ZERO)
        transactionCategory.activate()
        val questions = (0..20).flatMap { transactionCategory.questions() }
        val transactions = questions.map { it.toTransaction() }
        questions.forEach { question ->
            val transactionSum = calculateBalance(transactions)
            val answer = Answer(
                category = "transactions",
                teamName = "testteam",
                questionId = question.messageId,
                answer = transactionSum.toString()
            )
            transactionCategory.handle(answer)
        }
        val assessments = transactionCategory.events().map { (it as Assessment).isOk() }
        assertEquals(assessments.size, transactions.size)
        assertEquals(assessments.count { isOk -> isOk }, 1)
        assertEquals(assessments.count { isOk -> !isOk }, transactions.size - 1)
    }


    @Test
    fun `balance is calculated correctly`() {
        val t1 = listOf(
            innskudd(100),
            innskudd(101),
            uttrekk(200)
        )
        assertEquals(calculateBalance(t1), 1)
    }
    @Test
    fun `balance can be negative`(){
        val t2 = listOf(
            uttrekk(100),
            uttrekk(200)
        )
        assertEquals(calculateBalance(t2), -300)
    }

    @Test
    fun `event sourced`() {
        val first = Transactions(interval = Duration.ZERO, active = true)
        val second = Transactions(interval = Duration.ZERO, active = true)
        val questions = first.questions()
        val question = questions.first()
        val answer = Answer(
            category = question.category,
            questionId = question.id(),
            teamName = "tester",
            answer = calculateBalance (listOf(question).map { it.toTransaction() }).toString()
        )
        second.handle(question)
        second.handle(answer)
        val assessment = second.events().first() as Assessment
        assertEquals(answer.messageId, assessment.answerId)
        assertEquals(AssessmentStatus.SUCCESS, assessment.status)
    }

    @Test
    fun `appending new questions to event sourced works`() {
        val first = Transactions(interval = Duration.ZERO, active = true)
        val second = Transactions(interval = Duration.ZERO, active = true)
        val questions = first.questions()
        val question = questions.first()
        second.handle(question)
        val question2 = second.questions().first()
        val answer = Answer(
            category = question2.category,
            questionId = question2.id(),
            teamName = "tester",
            answer = calculateBalance (listOf(question, question2).map { it.toTransaction() }).toString()
        )
        second.handle(answer)
        val lastAssessment = second.events().first() as Assessment
        assertEquals(answer.messageId, lastAssessment.answerId)
        assertEquals(AssessmentStatus.SUCCESS, lastAssessment.status)
    }

    @Test
    fun `invalid transactions question is ignored`() {
        val transactions = Transactions(interval = Duration.ZERO, active = true)
        transactions.handle(Question(category = "transactions", question = "UTSKUDD 1000"))
        transactions.handle(Question(category = "transactions", question = "INNSKUDD acd"))
        assertEquals(0, transactions.stats().questionCount)
    }

    private fun innskudd(amount : Int) : Transaction = Transaction(type = INNSKUDD, amount = amount)
    private fun uttrekk(amount : Int) : Transaction = Transaction(type = UTTREKK, amount = amount)

    private fun Question.toTransaction() : Transaction {
        val type = this.question.split(" ")[0]
        val amount = this.question.split(" ")[1].toInt()
        return Transaction(type = TransactionType.valueOf(type), amount = amount)
    }
}