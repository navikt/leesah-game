package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Duration

internal class DeduplicationTest {


    @Test
    fun question() {
        val dedup = Deduplication(Duration.ZERO, 3, true)
        val question = dedup.questions().first()
        dedup.questions()
        dedup.questions()
        assertNotNull(question)

        dedup.check(
            answer("coolteam", question.messageId)
        )
        dedup.questions()

        val assessment = dedup.events().first().json()
        assertTrue(assessment.contains("coolteam"))
        assertTrue(assessment.contains("SUCCESS"))

        dedup.check(
            answer("notcoolteam", question.messageId)
        )
        dedup.check(
            answer("notcoolteam", question.messageId)
        )
        dedup.questions()
        val assessment2 = dedup.events()[1].json()
        assertTrue(assessment2.contains("notcoolteam"))
        assertTrue(assessment2.contains("FAILURE"))
        dedup.check(
            answer("notcoolteam", question.messageId)
        )
        val assessment3 = dedup.events().first().json()
        assertTrue(assessment3.contains("notcoolteam"))
        assertTrue(assessment3.contains("FAILURE"))

        dedup.check(
            resetAnswer("notcoolteam", question.messageId)
        )
        dedup.check(
            answer("notcoolteam", question.messageId)
        )
        dedup.questions()
        val assessment4 = dedup.events().first().json()
        assertTrue(assessment4.contains("notcoolteam"))
        assertTrue(assessment4.contains("SUCCESS"))
    }

    @Test
    fun withInterval() {
        val dedup = Deduplication(Duration.ofSeconds(1), 3, true)
        val question = dedup.questions().first()
        assertNotNull(question)

        dedup.check(
            answer("coolteam", question.messageId)
        )
        dedup.check(
            answer("coolteam", question.messageId)
        )
        dedup.questions()
        val assessment2 = dedup.events()[1].json()
        assertTrue(assessment2.contains("coolteam"))
        assertTrue(assessment2.contains("FAILURE"))
        dedup.check(
            resetAnswer("coolteam", question.messageId)
        )
        dedup.check(
            answer("coolteam", question.messageId)
        )
        Thread.sleep(1000)
        Thread.sleep(1000)
        val assessment3 = dedup.events().first().json()
        assertTrue(assessment3.contains("coolteam"))
        assertTrue(assessment3.contains("SUCCESS"))

    }

    private fun answer(team: String, qId: String) = Answer(
        category = "deduplication",
        teamName = team,
        questionId = qId,
        answer = "you wont dupe me!"
    )

    private fun resetAnswer(team: String, qId: String) = Answer(
        category = "deduplication",
        teamName = team,
        questionId = qId,
        answer = "you duped me!"
    )

}