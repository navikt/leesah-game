package no.nav.quizboard

import no.nav.quizrapid.Assessment
import no.nav.quizrapid.AssessmentStatus
import no.nav.quizrapid.Question

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class CategoryTest {

    val testName = "coolteam"
    val testCategory = "some-question"
    val next get() = kotlin.random.Random.nextInt(100)

    @Test
    fun handleAssessment() {
        val categories = mutableListOf<Category>()
        val q1 = question()
        val q2 = question()
        categories.handle(q1)
        categories.handle(q2)
        assertEquals(2, categories.size)
        assertEquals(0, categories.teams().size)

        categories.handle(okAssessment(q1))
        assertEquals(1, categories.teams().size)

        val results = categories.result(categories.teams().first())
        assertEquals(results.size, 2)
        assertTrue(results.any { it.status == Status.OK })
    }

    @Test
    fun okCount() {
        val categories = mutableListOf<Category>()
        val q1 = question()
        val q2 = question()
        categories.handle(q1)
        categories.handle(q2)
        assertEquals(2, categories.size)
        assertEquals(0, categories.teams().size)

        categories.handle(okAssessment(q1))

        val results = categories.result(categories.teams().first())
        assertEquals(results[0].okCount, 1)
        assertEquals(results[1].okCount, 0)
    }


    private fun okAssessment(question: Question) =
        Assessment(
            UUID.randomUUID().toString(),
            question.category,
            testName,
            question.id(),
            UUID.randomUUID().toString(),
            AssessmentStatus.SUCCESS
        )

    private fun question() = Question(UUID.randomUUID().toString(), "c${next}", "cat?")
}