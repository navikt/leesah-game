package no.nav.quizmaster.questions

import com.fasterxml.jackson.module.kotlin.readValue
import no.nav.quizmaster.QuizMaster
import no.nav.quizrapid.Answer
import no.nav.quizrapid.Assessment
import no.nav.quizrapid.AssessmentStatus
import no.nav.quizrapid.objectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EventSourcedQuizMasterTest {

    @Test
    fun `quizmaster handles previously published questions`() {
        val question = RegisterTeam().questions()[0]
        val qm = QuizMaster()
        qm.handle(question)
        qm.handle(Answer(category = question.category, teamName = "new-team", answer = "new-team", questionId = question.id()))
        val events = qm.events()
        assertEquals(1, events.size)
        val assessment = (objectMapper.readValue<Assessment>(events.first()))
        assertEquals(AssessmentStatus.SUCCESS, assessment.status)
        assertEquals(question.id(), assessment.questionId)
        val events2 = qm.events()
        assertEquals(0, events2.size)
    }
}