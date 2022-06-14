package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class PendingQuestionTest {


    @Test
    fun accept() {
        val pq = PendingQuestion("to pend", true, "who?")
        val question = pq.questions()[0]
        val answer = Answer(category = "to pend", teamName = "testTeam", questionId = question.id(), answer = "myanswer")
        pq.handle(answer)
        pq.accept(answer.id())
        assertEquals(1, pq.events().size)

        // activate
        pq.activate()
        val question2 = pq.questions()[0]
        val answer2 = Answer(category = "to pend", teamName = "testTeam", questionId = question.id(), answer = "myanswer")
        pq.handle(answer2)
        pq.accept(answer.id())
        assertEquals(0, pq.events().size)

        val answer3 = Answer(category = "to pend", teamName = "testTeam", questionId = question2.id(), answer = "myanswer")
        pq.handle(answer3)
        pq.accept(answer3.id())
        assertEquals(1, pq.events().size)
    }

    @Test
    fun newestAnswerWins() {
        val pq = PendingQuestion("to pend", true, "who?")
        val question = pq.questions()[0]
        val answer = Answer(category = "to pend", teamName = "testTeam", questionId = question.id(), answer = "myanswer")
        val answer2 = Answer(category = "to pend", teamName = "testTeam", questionId = question.id(), answer = "myanswer")
        pq.handle(answer)
        pq.handle(answer2)
        assertEquals(answer2.id(), pq.stats().pendingAnswers[0].answerId)
    }
}