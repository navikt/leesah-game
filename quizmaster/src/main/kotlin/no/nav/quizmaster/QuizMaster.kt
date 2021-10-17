package no.nav.quizmaster

import com.fasterxml.jackson.core.JacksonException
import no.nav.quizmaster.questions.Arithmetic
import no.nav.quizmaster.questions.RegisterTeam
import org.slf4j.LoggerFactory
import java.time.Duration

class QuizMaster {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)
    private val questions = listOf(RegisterTeam(), Arithmetic(Duration.ofMinutes(1)))

    fun handle(message: String) {
        try {
            questions.forEach { question -> question.handle(message) }
        } catch (e: JacksonException) {
            logger.warn("failed to parse, skipping message", message)
            logger.warn(e.toString())
        }
    }


    fun events(): List<String> {
        val outQuestions: List<String> = questions
            .fold(emptyList()) { list, question ->
                list + question.questions().json()
            }

        val outEvents: List<String> = questions
            .fold(emptyList()) { list, question ->
                list + question.events().json()
            }
        return outEvents + outQuestions
    }
}

private fun Iterable<Message>.json() = map { it.json() }