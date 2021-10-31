package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Assessment
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Duration

internal class TransactionsTest {

    @Test
    fun `correct answer yields one correct assessment per transaction`() {
        val transactionCategory = Transactions()
        transactionCategory.activate()
        val questions = transactionCategory.questions()
        val transactionSum = questions.sumOf {
            it.question.toInt()
        }.toString()
        val answerToCorrectMessage = Answer(
            category = "transactions",
            teamName = "testteam",
            questionId = questions.first().messageId,
            answer = transactionSum
        )
        transactionCategory.check(answerToCorrectMessage)
        val correctAssessments = transactionCategory.events()
        assertTrue(correctAssessments.size == questions.size)
        assertTrue((correctAssessments.all { (it as Assessment).isOk() }))
    }

    @Test
    fun `wrong answer yields one wrong assessment per transaction`() {
        val transactionCategory = Transactions()
        transactionCategory.activate()
        val questions = transactionCategory.questions()
        val transactionSum = questions.sumOf {
            it.question.toInt()
        }
        val answerToWrongMessage = Answer(
            category = "transactions",
            teamName = "testteam",
            questionId = questions.last().messageId,
            answer = (transactionSum - 1).toString()
        )
        transactionCategory.check(answerToWrongMessage)
        val wrongAssessment = transactionCategory.events()
        assertTrue(wrongAssessment.size == questions.size)
        assertTrue((wrongAssessment.all { (it as Assessment).isWrong() }))
    }
}