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

        val stringList = questionString.split("i")[1].replace(" ", "").replace("[", "").replace("]", "").split(",")
        val intList = stringList.map { it.toInt() }

        return if (minmaxValue == "LAVESTE") {
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
    }

    @Test
    fun questionIsAnsweredSuccess() {
        val minMaxInt = MinMaxInt(Duration.ZERO)
        minMaxInt.activate()
        val question = minMaxInt.questions().first()
        val result = answer(question)

        minMaxInt.handle(Answer(category = "min-max-int", questionId = question.messageId, teamName = "Tandis", answer = result.toString()))

        val assessment1 = minMaxInt.events().first().json()
        assertTrue(assessment1.contains("Tandis"))
        assertTrue(assessment1.contains("SUCCESS"))
    }
}
