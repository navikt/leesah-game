package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Assessment
import no.nav.quizrapid.AssessmentStatus
import no.nav.quizrapid.Question
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.Duration
import java.util.*

internal class Base64EchoTest {

    @Test
    fun newQuestions() {
        val base64Echo = Base64Echo(2, true, Duration.ZERO)
        assertTrue(base64Echo.questions().isNotEmpty())

        val question = base64Echo.questions().first()
        val result = decode(question)
        base64Echo.handle(Answer(category = "base64", questionId = question.messageId, teamName = "coolteam", answer = result))
        val assessment1 = base64Echo.events().first().json()
        assertTrue(assessment1.contains("coolteam"))
        assertTrue(assessment1.contains("SUCCESS"))

        base64Echo.handle(Answer(category = "base64", questionId = question.messageId, teamName = "coolteam", answer = "basanswer"))
        val assessment2 = base64Echo.events().first().json()
        assertTrue(assessment2.contains("coolteam"))
        assertTrue(assessment2.contains("FAILURE"))
    }

    private fun decode(question: Question): String {
        val result = String(Base64.getDecoder().decode(question.question.split(" ")[1]))
        return result
    }

    @Test
    fun `event sourcing`() {
        val first = Base64Echo(1, true, Duration.ZERO)
        val second = Base64Echo(1, true, Duration.ZERO)
        val question = first.questions().first()
        second.handle(question)
        val answer = Answer(category = "base64", questionId = question.id(), teamName = "coolteam", answer = decode(question))
        second.handle(answer)
        val assessment = second.events().first() as Assessment
        assertEquals(answer.id(), assessment.answerId)
        assertEquals(AssessmentStatus.SUCCESS, assessment.status)
    }
}