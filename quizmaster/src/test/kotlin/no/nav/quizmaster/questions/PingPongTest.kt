package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import java.time.Duration

internal class PingPongTest {


    @Test
    fun newQuestions() {
        val pp = PingPong(2, true, Duration.ZERO)
        assertTrue(pp.questions().isNotEmpty())

        val question = pp.questions().first()
        pp.handle(answer("coolteam", question.messageId))
        val assessment = pp.events().first()
        assertTrue(assessment.json().contains("coolteam") && assessment.json().contains("SUCCESS"))

        pp.handle(wrongAnswer("notcoolteam", question.messageId))
        val assessment2 = pp.events().first()
        assertTrue(assessment2.json().contains("notcoolteam") && assessment2.json().contains("FAILURE"))

    }

    @Test
    fun `interval of questions`() {
        val pp = PingPong(2, true, Duration.ofSeconds(1))
        assertTrue(pp.questions().isNotEmpty())
        assertTrue(pp.questions().isEmpty())
        Thread.sleep(1000)
        assertTrue(pp.questions().isNotEmpty())
        assertTrue(pp.questions().isEmpty())

    }

    private fun answer(team: String, qId: String) = Answer(
        category = "ping-pong",
        teamName = team,
        questionId = qId,
        answer = "pong"
    )

    private fun wrongAnswer(team: String, qId: String) = Answer(
        category = "ping-pong",
        teamName = team,
        questionId = qId,
        answer = "notpong"
    )
}