package no.nav.quizmaster.questions

import Feedback
import no.nav.quizrapid.Answer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class FeedbackTest {

    @Test
    fun `must be activated`() {
        val registerQuestion = Feedback(1,false)
        assertTrue(registerQuestion.questions().isEmpty())
        registerQuestion.activate()
        assertTrue(registerQuestion.questions().isNotEmpty())
    }

    @Test
    fun newQuestion() {
        val feedback = Feedback(2,true)
        assertTrue(feedback.questions().isNotEmpty())

        val question = feedback.questions().first()
        feedback.handle(answer("Tandis", question.messageId))
        val assessment = feedback.events().first()
        assertTrue(assessment.json().contains("Tandis") && assessment.json().contains("SUCCESS"))
    }

    @Test
    fun events() {
        val question = Feedback(2,true)
        val q = question.questions().first()
        question.check(answer(q.id(), "team1"))
        question.check(answer(q.id(), "team2"))
        assertTrue(question.events().size == 2)
        assertTrue(question.events().isEmpty())
    }

    private fun answer(team: String, qId: String) = Answer(
        category = "feedback",
        teamName = team,
        questionId = qId,
        answer = "I love it"
    )
}