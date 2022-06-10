package no.nav.quizmaster.questions

import no.nav.quizmaster.QuizMaster
import no.nav.quizrapid.Answer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
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
        assertTrue(events[0].contains("ASSESSMENT"))
    }
}