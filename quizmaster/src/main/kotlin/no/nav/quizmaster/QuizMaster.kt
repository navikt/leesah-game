package no.nav.quizmaster.no.nav.quizmaster

import com.fasterxml.jackson.core.JsonParseException
import no.nav.quizmaster.Message
import no.nav.quizmaster.questions.RegisterTeam
import org.slf4j.LoggerFactory

class QuizMaster {

    private val logger = LoggerFactory.getLogger(this.javaClass.name)
    private val questions = listOf(RegisterTeam())

    fun handle(message: String) {
        try {
            questions.forEach { question -> question.handle(message) }
        } catch (e: JsonParseException) {
            logger.warn("failed to parse, skipping message", message)
            logger.debug(e.toString())
        }
    }


    fun events(): List<String> {
        val outQuestions: List<String> = questions
            .filter { it.shouldBeAsked() }
            .fold(mutableListOf()) { list, question ->
                return list + question.question().json()
            }

        val outEvents: List<String> = questions
            .fold(mutableListOf()) { list, question ->
                return list + question.events().json()
            }
        return outEvents + outQuestions
    }
}

private fun Iterable<Message>.json() = map { it.json() }