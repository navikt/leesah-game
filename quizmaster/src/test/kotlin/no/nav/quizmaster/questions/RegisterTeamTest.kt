package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Disabled
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
    fun `is not a hex`() {
        val registerQuestion = RegisterTeam(true)
        val question = registerQuestion.questions()
        val qId = question.first().id()

        registerQuestion.newTeam(answer(qId, "Tandis","003f"))
        val assessments = registerQuestion.events()
        assertFalse(assessments.size == 1)
    }

    @Test
    fun `max count 1 question`() {
        val registerQuestion = RegisterTeam(true)
        val q = registerQuestion.questions().first()

        registerQuestion.check(answer(q.id(), "test","ffffff"))
        registerQuestion.check(answer(q.id(), "test","000000"))
        assertTrue(registerQuestion.events().size == 1)
        assertTrue(registerQuestion.events().isEmpty())
    }

    @Test
    fun `max length teamname`(){
        val registerQuestion = RegisterTeam(true)
        val q = registerQuestion.questions()
        val qId = q.first().id()

        registerQuestion.check(answer(qId, "1234567890123456789012345123456","000000"))
        registerQuestion.check(answer(qId, "12346789012345678901234","000000"))

        val assessments = registerQuestion.events()

        assertTrue(assessments.size == 1)
    }


    @Test
    fun `right question id`() {
        val registerQuestion = RegisterTeam(true)
        val question = registerQuestion.questions()
        val qId = question.first().id()

        registerQuestion.handle(answer(qId, "test","ffffff"))
        registerQuestion.handle(answer("wrongid", "test","ff1100"))
        registerQuestion.handle(answer(qId, "test","000000"))

        val assessments = registerQuestion.events()
        assertTrue(assessments.size == 1)
        println(assessments.first().json())
        assertTrue(assessments.first().json().contains("SUCCESS"))
        assertTrue(assessments.first().json().contains(qId))
        assertFalse(assessments.first().json().contains("wrongid"))
    }

    private fun answer(qid: String, teamName: String, answer: String) =
        Answer(category = "team-registration", teamName = teamName, questionId = qid, answer = answer)
}
