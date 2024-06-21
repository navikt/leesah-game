package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Assessment
import no.nav.quizrapid.AssessmentStatus
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.Duration

internal class IsAPrimeTest {



    @Test
    fun primeNumber() {
        val isAPrime = IsAPrime(2, true, Duration.ZERO)
        Assertions.assertTrue(isAPrime.questions().isNotEmpty())

        val question = isAPrime.questions().first()
        val answer = isPrime(question.question.split(" ")[4].trim().toInt()).answer("coolteam", question.messageId)
        isAPrime.handle(answer)
        val assessment = isAPrime.events().first()
        Assertions.assertTrue(assessment.json().contains("coolteam") && assessment.json().contains("SUCCESS"))
    }

    @Test
    fun `event sourcing`() {
        val first = IsAPrime(1, true, Duration.ZERO)
        val second = IsAPrime(1, true, Duration.ZERO)
        val question = first.questions().first()
        val questionNumber = IsAPrime.questionNumber(question)
        val answer = Answer(category = question.category, questionId =  question.id(), teamName = "tester", answer = isPrime(questionNumber).toString() )
        second.handle(question)
        second.handle(answer)
        val assessment = second.events().first() as Assessment
        assertEquals(answer.id(), assessment.answerId)
        assertEquals(AssessmentStatus.SUCCESS, assessment.status)
    }
    @Test
    fun `answers to not event sourced answers are ignored`() {
        val first = IsAPrime(1, true, Duration.ZERO)
        val second = IsAPrime(1, true, Duration.ZERO)
        val question = first.questions().first()
        val questionNumber = IsAPrime.questionNumber(question)
        val answer = Answer(category = question.category, questionId =  question.id(), teamName = "tester", answer = isPrime(questionNumber).toString() )
        second.handle(answer)
        assertEquals(0, second.events().size)
    }

    private fun isPrime(num: Int): Boolean {
        var flag = false
        for (i in 2..num / 2) {
            // condition for nonprime number
            if (num % i == 0) {
                flag = true
                break
            }
        }
        return !flag
    }

    private fun Boolean.answer(team: String, qId: String) = Answer(
        category = "is-a-prime",
        teamName = team,
        questionId = qId,
        answer = this.toString().lowercase()
    )
}