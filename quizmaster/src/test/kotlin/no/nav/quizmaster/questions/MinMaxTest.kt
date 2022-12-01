package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Duration

internal class MinMaxTest {
    private fun answer(question: Question): Int {
        val questionString = question.question
        val minmaxValue = questionString.split(' ')[0]

        val stringList = questionString.split("i")[1].replace(" ", "").replace("[","").replace("]","").split(",")
        val intList = stringList.map { it.toInt() }

        println("minMaxValue$minmaxValue")
        println("questionString$questionString")
        return if(minmaxValue === "LAVESTE") {
            intList.minOf { it }
        } else {
            intList.maxOf { it }
        }
    }

    @Test
    fun question() {
        val minMaxInt = MinMaxInt(Duration.ZERO)
        minMaxInt.activate()
        assertTrue(minMaxInt.questions().isNotEmpty())
        assertTrue(MinMaxInt(Duration.ofHours(1)).questions().isEmpty())
        val question = minMaxInt.questions().first()

        val result = answer(question)

        minMaxInt.handle(Answer(category = "min-max-int", questionId = question.messageId, teamName = "Tandis", answer = result.toString()))

        println("SVARET$result")
        val assessment1 = minMaxInt.events().first().json()
        assertTrue(assessment1.contains("Tandis"))
        assertTrue(assessment1.contains(result.toString()))

//        val art = Arithmetic(Duration.ZERO)
//        art.activate()
//        val questions = art.questions()
//        val result = arithmeticSolver(questions.first())
//        assertNotNull(result)
//        art.check(
//            Answer(
//                category = "arithmetic",
//                teamName = "coolteam",
//                questionId = questions.first().messageId,
//                answer = "$result"
//            )
//        )
//        art.check(
//            Answer(
//                category = "arithmetic",
//                teamName = "coolteam",
//                questionId = questions.first().messageId,
//                answer = "${result!! + 1}"
//            )
//        )
//        art.events().also {
//            assertTrue(it.size == 2)
//            assertTrue(it[0].json().contains("SUCCESS"))
//            assertTrue(it[1].json().contains("FAILURE"))
//        }
    }

//    @Test
//    fun `event sourcing`() {
//        val first = Arithmetic(Duration.ZERO, active = true)
//        val question = first.questions().first()
//
//        val second = Arithmetic(Duration.ZERO, active = false)
//        second.handle(question)
//        val answer  = Answer(category = question.category, teamName = "tester", questionId = question.id(), answer = arithmeticSolver(question)!!.toString())
//        second.handle(answer)
//        val assessment = (second.events().first() as Assessment)
//        assertEquals(assessment.answerId, answer.id())
//        assertEquals(assessment.status, AssessmentStatus.SUCCESS)
//    }
//
//    private fun arithmeticSolver(question: Question): Int? {
//        val exp = question.question.split(" ")
//        val result = operatorMap[exp[1]]?.invoke(exp[0].toInt(), exp[2].toInt())
//        return result
//    }


}
