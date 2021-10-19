package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RegisterTeamTest {

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun onlyOnce() {
        val registerQuestion = RegisterTeam()

        assertTrue(registerQuestion.questions().isNotEmpty())
        assertTrue(registerQuestion.questions().isEmpty())
    }

    @Test
    fun events() {
        val registerQuestion = RegisterTeam()
        registerQuestion.questions()
        registerQuestion.check(answer())
        registerQuestion.check(answer())
        assertTrue(registerQuestion.events().size == 1)
        assertTrue(registerQuestion.events().isEmpty())
    }

    private fun answer() =
        Answer(category = "team-registration", teamName = "", questionId = "question1", answer = "coolteam")
}