package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Assessment
import no.nav.quizrapid.AssessmentStatus
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

internal class GrunnbeløpTest {

    @Test
    fun `generates questions`(){
        val grunnbeløpQuestion = Grunnbeløp()
        assertTrue(grunnbeløpQuestion.questions().isEmpty())
        grunnbeløpQuestion.activate()
        assertTrue(grunnbeløpQuestion.questions().isNotEmpty())
    }

    @Test
    fun `Answer OK`(){
        val grunnbeløpQuestion = Grunnbeløp(active = true, maxCount = 1000)
        val questions = grunnbeløpQuestion.questions()
        val q = questions.first()
        for (i in 1..1000) {
            val date = LocalDate.parse(q.question.split(":")[1].removePrefix(" "))
            val answer = grunnbeløpQuestion.grunnbeløpFor(date)
            grunnbeløpQuestion.handle(Answer(category = "grunnbeløp", teamName = "tester", questionId = q.id(), answer = answer.toString()))
            val assessment =  (grunnbeløpQuestion.events().first() as Assessment)
            assertEquals(AssessmentStatus.SUCCESS, assessment.status)
        }
    }


    @Test
    fun `Wrong answer`(){
        val grunnbeløpQuestion = Grunnbeløp(active = true, maxCount = 1000)
        val questions = grunnbeløpQuestion.questions()
        val q = questions.first()
        grunnbeløpQuestion.handle(Answer(category = "grunnbeløp", teamName = "tester", questionId = q.id(), answer = 1000.toString()))
        val assessment =  (grunnbeløpQuestion.events().first() as Assessment)
        assertEquals(AssessmentStatus.FAILURE, assessment.status)
    }

    @Test
    fun `Right Grunnbeløp for date`(){
        val grunnbeløpQuestion = Grunnbeløp(active = true, maxCount = 1000)
        assertEquals(null, grunnbeløpQuestion.grunnbeløpFor(1.januar(1966)))
        assertEquals(5400, grunnbeløpQuestion.grunnbeløpFor(1.januar(1967)))
        assertEquals(106399, grunnbeløpQuestion.grunnbeløpFor(30.april(2022)))
        assertEquals(106399, grunnbeløpQuestion.grunnbeløpFor(30.april(2022)))
        assertEquals(111477, grunnbeløpQuestion.grunnbeløpFor(1.mai(2022)))
        assertEquals(111477, grunnbeløpQuestion.grunnbeløpFor(2.mai(2022)))
    }
}