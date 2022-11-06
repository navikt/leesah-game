package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Assessment
import no.nav.quizrapid.AssessmentStatus
import no.nav.quizrapid.Question
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Duration

internal class NAVTest {

    @BeforeEach
    fun setUp() {
    }

    @Test
    fun `vi rekrutterer på detsombetyrnoe`() {
        val navQuestions = NAV(Duration.ZERO)
        navQuestions.activate()
        val q = navQuestions.questions().filterQuestionsByContent("rekruttering")


        val correctAnswers = listOf(
            "https://detsombetyrnoe.no",
            "Det som betyr noe",
            "detsombetyrnoe",
            "detsombetyrnoe.no",
            "www.detsombetyrnoe.no"
        )
        correctAnswers.forEach {
            navQuestions.check(answerNavQuestion(q, it))
        }
        val correctAssessments = navQuestions.events()
        assertTrue(correctAssessments.size == correctAnswers.size)
        assertTrue((correctAssessments.all { (it as Assessment).isOk() }))

        assertTrue(navQuestions.events().isEmpty())

        val incorrectAnswers = listOf(
            "https://detsombetyrnoget.no",
            "Det om betyr noe",
            "nav.no",
        )

        incorrectAnswers.forEach {
            navQuestions.check(answerNavQuestion(q, it))
        }
        val incorrectAssessments = navQuestions.events()
        assertTrue(incorrectAssessments.size == incorrectAnswers.size)
        assertTrue((incorrectAssessments.all { (it as Assessment).isWrong() }))

        assertTrue(navQuestions.events().isEmpty())
    }

    @Test
    fun `NAIS er vår applikasjonsplattform`() {
        val navQuestions = NAV(Duration.ZERO)
        navQuestions.activate()
        navQuestions.questions() // skip first question
        val q = navQuestions.questions().filterQuestionsByContent("applikasjonsplattformen")

//  TODO: må akseptere flere mulige svar på samme spørsmål for denne
//  "NAV's Application Infrastructure Service",
//  "NAV Application Infrastructure Service",
        val correctAnswers = listOf(
            "https://nais.io",
            "nais.io",
            "nais",
            "NAIS",
            "N A I S",
        )
        correctAnswers.forEach {
            navQuestions.check(answerNavQuestion(q, it))
        }
        val correctAssessments = navQuestions.events()
        assertTrue(correctAssessments.size == correctAnswers.size)
        assertTrue((correctAssessments.all { (it as Assessment).isOk() }))

        assertTrue(navQuestions.events().isEmpty())

        val incorrectAnswers = listOf(
            "nias",
            "noe helt annet",
        )

        incorrectAnswers.forEach {
            navQuestions.check(answerNavQuestion(q, it))
        }
        val incorrectAssessments = navQuestions.events()
        assertTrue(incorrectAssessments.size == incorrectAnswers.size)
        assertTrue((incorrectAssessments.all { (it as Assessment).isWrong() }))

        assertTrue(navQuestions.events().isEmpty())
    }

    @Test
    fun `event sourcing`() {
        val first = NAV(active = true, frequency = Duration.ZERO)
        val second = NAV(active = true, frequency = Duration.ZERO)
        val question = first.questions().first()
        val question2 = first.questions().first()
        val answer = answerNavQuestion(question, "detsombetyrnoe")
        val answer2 = answerNavQuestion(question2, "nais")
        second.handle(question)
        second.handle(answer)
        val assessment = second.events().first() as Assessment
        assertEquals(answer.id(), assessment.answerId)
        assertEquals(AssessmentStatus.SUCCESS, assessment.status)
        second.handle(answer2)
        assertEquals(0, second.events().size)
    }


    private fun answerNavQuestion(question : Question, answer : String) =
        Answer(category = "NAV", questionId = question.id(), teamName = "test-team", answer = answer)

    private fun List<Question>.filterQuestionsByContent(filter : String) : Question = this.first {
        it.question.contains(filter)
    }
}