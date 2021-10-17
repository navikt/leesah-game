package no.nav.quizmaster.questions

import no.nav.quizmaster.Answer
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class RegisterTeamTest {

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun shouldBePosted() {
        val registerQuestion = RegisterTeam()

        assertTrue(registerQuestion.shouldBeAsked())
        registerQuestion.question()
        assertFalse(registerQuestion.shouldBeAsked())
    }

    @Test
    fun events() {
        val registerQuestion = RegisterTeam()
        registerQuestion.handle(answer().json())
        registerQuestion.handle(answer().json())
        assertTrue(registerQuestion.events().size == 1)
        assertTrue(registerQuestion.events().isEmpty())
    }

    private fun answer() =
        Answer(category = RegisterTeam.CATEGORY, teamName = "", answer = "coolteam")
}