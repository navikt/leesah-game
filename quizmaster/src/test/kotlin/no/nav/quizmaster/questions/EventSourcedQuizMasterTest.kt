package no.nav.quizmaster.questions

import no.nav.quizmaster.QuizMaster
import no.nav.quizrapid.Answer
import no.nav.quizrapid.Assessment
import no.nav.quizrapid.AssessmentStatus
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EventSourcedQuizMasterTest {

    @Test
    fun `quizmaster handles previously published questions`() {
        val question = RegisterTeam(true).questions()[0]
        val qm = QuizMaster()
        qm.handle(question)
        qm.handle(Answer(category = question.category, teamName = "new-team", answer = "new-team", questionId = question.id()))
        val events = qm.messages()
        assertEquals(1, events.size)
        val assessment = events.first() as Assessment
        assertEquals(AssessmentStatus.SUCCESS, assessment.status)
        assertEquals(question.id(), assessment.questionId)
        val events2 = qm.messages()
        assertEquals(0, events2.size)
    }
}