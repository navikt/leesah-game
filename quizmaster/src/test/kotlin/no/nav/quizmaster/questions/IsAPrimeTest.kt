package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.Duration

internal class IsAPrimeTest {



    @Test
    fun primeNumber() {
        val isAPrime = IsAPrime(2, true, Duration.ZERO)
        Assertions.assertTrue(isAPrime.questions().isNotEmpty())

        val question = isAPrime.questions().first()
        val answer = isPrime(question.question.split("?")[1].trim().toInt()).answer("coolteam", question.messageId)
        isAPrime.handle(answer)
        val assessment = isAPrime.events().first()
        Assertions.assertTrue(assessment.json().contains("coolteam") && assessment.json().contains("SUCCESS"))
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