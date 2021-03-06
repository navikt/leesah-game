package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.Duration

internal class ArithmeticTest {

    @BeforeEach
    fun setUp() {
    }

    val operatorMap = mapOf<String, (Int, Int) -> Int>(
        "+" to { first, last -> first + last },
        "*" to { first, last -> first * last },
        "-" to { first, last -> first - last },
        "/" to { first, last -> first / last },
    )

    @Test
    fun question() {
        val art0 = Arithmetic(Duration.ZERO)
        art0.activate()
        assertTrue(art0.questions().isNotEmpty())
        assertTrue(Arithmetic(Duration.ofHours(1)).questions().isEmpty())


        val art = Arithmetic(Duration.ZERO)
        art.activate()
        val questions = art.questions()
        val exp = questions.first().question.split(" ")
        val result = operatorMap[exp[1]]?.invoke(exp[0].toInt(), exp[2].toInt())
        assertNotNull(result)
        art.check(
            Answer(
                category = "arithmetic",
                teamName = "coolteam",
                questionId = questions.first().messageId,
                answer = "$result"
            )
        )
        art.check(
            Answer(
                category = "arithmetic",
                teamName = "coolteam",
                questionId = questions.first().messageId,
                answer = "${result!! + 1}"
            )
        )
        art.events().also {
            assertTrue(it.size == 2)
            assertTrue(it[0].json().contains("SUCCESS"))
            assertTrue(it[1].json().contains("FAILURE"))
        }
    }
}