package no.nav.quizmaster.questions

import no.nav.quizmaster.Answer
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
        assertTrue(Arithmetic(Duration.ZERO).questions().isNotEmpty())
        assertTrue(Arithmetic(Duration.ofHours(1)).questions().isEmpty())


        val art = Arithmetic(Duration.ZERO)
        val questions = art.questions()
        val exp = questions.first().question.split(" ")
        val result = operatorMap[exp[1]]?.invoke(exp[0].toInt(), exp[2].toInt())
        assertNotNull(result)
        art.handle(
            Answer(
                category = "arithmetic",
                teamName = "coolteam",
                questionId = questions.first().messageId,
                answer = "$result"
            ).json()
        )
        art.handle(
            Answer(
                category = "arithmetic",
                teamName = "coolteam",
                questionId = questions.first().messageId,
                answer = "${result!! + 1}"
            ).json()
        )
        assertTrue(art.events().size == 1)
    }
}