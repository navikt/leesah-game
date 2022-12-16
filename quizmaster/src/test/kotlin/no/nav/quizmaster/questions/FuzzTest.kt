package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import java.time.Duration

class FuzzTest {

    val questions = listOf(
        RegisterTeam(true),
        Arithmetic(Duration.ZERO, true),
        NAV(Duration.ZERO, true),
        Deduplication(Duration.ZERO, 1, true),
        Transactions(1, true, Duration.ZERO),
        PingPong(1, true, Duration.ZERO),
        Base64Echo(1, true, Duration.ZERO),
        IsAPrime(maxCount = 1, interval = Duration.ZERO, active = true),
        Grunnbeløp(maxCount = 1, interval = Duration.ZERO, active = true),
        PendingQuestion("pending-question", active = true, question = "test question")
    )


    @Test
    fun fuzz() {
        questions.forEach {questionCategory ->
            val question = questionCategory.questions().first()
            answers(question.category, question.id()).forEach { answer ->
                assertDoesNotThrow { questionCategory.handle(answer) }
            }
        }
    }

    private fun answers(category: String, questionId: String): List<Answer> {
        val data = listOf("", "1", "AAAA", "$", " ", "\n", "\uD83D\uDE0B")
        return data.zip(data.reversed()) { a, b -> Answer(category = category, questionId=questionId, teamName = a, answer = b)}
    }
}