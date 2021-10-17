package no.nav.quizmaster.questions

import no.nav.quizmaster.*
import no.nav.quizmaster.containsValue
import org.slf4j.LoggerFactory

abstract class QuestionFactory(protected val category: String) {
    val logger = LoggerFactory.getLogger(this.javaClass.name)
    private val outEvents = mutableListOf<Assessment>()

    internal fun handle(event: String): Boolean {
        val teamRegistration: Answer = tryFromRaw(event) {
            it.containsValue("type", MessageType.ANSWER.name) &&
                    it.containsValue("category", category)
        } ?: return false
        handle(teamRegistration)
        return true
    }

    protected abstract fun handle(answer: Answer)
    abstract fun questions(): List<Question>

    fun events(): List<Message> {
        val out = outEvents.toList()
        outEvents.clear()
        return out
    }

    protected fun publish(teamName: String, questionId: String, answerId: String) {
        val assessment =
            Assessment(
                category = category,
                teamName = teamName,
                questionId = questionId,
                answerId = answerId
            )
        logger.debug("publishing assessment", assessment)
        outEvents.add(assessment)
    }
}