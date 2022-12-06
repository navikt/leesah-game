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
    fun `is not a hex`() {
        val registerQuestion = RegisterTeam(true)
        val question = registerQuestion.questions()
        val qId = question.first().id()

        registerQuestion.handle(answer(qId, "003f"))
        val assessments = registerQuestion.events()

        assertTrue(assessments.size == 2)
        assertTrue(assessments.first().json().contains("003f"))
        assertTrue(assessments[1].json().contains("003f"))
    }

    @Test
    fun events() {
        val registerQuestion = RegisterTeam(true)
        val q = registerQuestion.questions().first()

        registerQuestion.check(answer(q.id(), "ffffff"))
        registerQuestion.check(answer(q.id(), "000000"))
        assertTrue(registerQuestion.events().size == 2)
        assertTrue(registerQuestion.events().isEmpty())
    }

    @Test
    fun `wrong question id`() {
        val registerQuestion = RegisterTeam(true)
        val question = registerQuestion.questions()
        val qId = question.first().id()

        registerQuestion.handle(answer(qId, "ffffff"))
        registerQuestion.handle(answer("wrongid", "ff1100"))
        registerQuestion.handle(answer(qId, "000000"))

        val assessments = registerQuestion.events()
        assertTrue(assessments.size == 2)
        assertTrue(assessments.first().json().contains("ffffff"))
        assertTrue(assessments[1].json().contains("000000"))
    }

    private fun answer(qid: String, answer: String) =
        Answer(category = "team-registration", teamName = "Tandis", questionId = qid, answer = answer)
}
