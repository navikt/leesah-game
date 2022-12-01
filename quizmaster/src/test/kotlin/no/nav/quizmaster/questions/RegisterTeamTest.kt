package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class RegisterTeamTest {


    @Test
    fun onlyOnce() {
        val registerQuestion = RegisterTeam(true)

        assertTrue(registerQuestion.questions().isNotEmpty())
        assertTrue(registerQuestion.questions().isEmpty())
    }

    @Test
    fun `must be activated`() {
        val registerQuestion = RegisterTeam(false)
        assertTrue(registerQuestion.questions().isEmpty())
        registerQuestion.activate()
        assertTrue(registerQuestion.questions().isNotEmpty())
    }

    @Test
    fun events() {
        val registerQuestion = RegisterTeam(true)
        val q = registerQuestion.questions().first()
        registerQuestion.check(answer(q.id(), "team1"))
        registerQuestion.check(answer(q.id(), "team2"))
        assertTrue(registerQuestion.events().size == 2)
        assertTrue(registerQuestion.events().isEmpty())
    }

    @Test
    fun `wrong question id`() {
        val registerQuestion = RegisterTeam(true)
        val question = registerQuestion.questions()
        val qId = question.first().id()

        registerQuestion.handle(answer(qId, "team1"))
        registerQuestion.handle(answer("wrongid", "team2"))
        registerQuestion.handle(answer(qId, "team3"))
        val assessments = registerQuestion.events()
        assertTrue(assessments.size == 2)
        assertTrue(assessments.first().json().contains("team1"))
        assertTrue(assessments[1].json().contains("team3"))
    }

    private fun answer(qid: String, teamName: String) =
        Answer(category = "team-registration", teamName = "", questionId = qid, answer = teamName)
}