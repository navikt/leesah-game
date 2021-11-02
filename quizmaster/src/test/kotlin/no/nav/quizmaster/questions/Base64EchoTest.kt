package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
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
        val result = String(Base64.getDecoder().decode(question.question.split(" ")[1]))
        base64Echo.handle(Answer(category = "base64", questionId = question.messageId, teamName = "coolteam", answer = result))
        val assessment1 = base64Echo.events().first().json()
        assertTrue(assessment1.contains("coolteam"))
        assertTrue(assessment1.contains("SUCCESS"))

        base64Echo.handle(Answer(category = "base64", questionId = question.messageId, teamName = "coolteam", answer = "basanswer"))
        val assessment2 = base64Echo.events().first().json()
        assertTrue(assessment2.contains("coolteam"))
        assertTrue(assessment2.contains("FAILURE"))
    }
}