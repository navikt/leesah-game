package no.nav.quizmaster.questions

import no.nav.quizmaster.*
import org.slf4j.LoggerFactory


class RegisterTeam {

    companion object {
        internal const val CATEGORY = "team-registration"
    }

    private val logger = LoggerFactory.getLogger(this.javaClass.name)
    private var questionPostedId: String? = null
    private val teams = mutableListOf<String>()
    private val outEvents = mutableListOf<Assessment>()

    internal fun handle(event: String): Boolean {
        val teamRegistration: Answer = tryFromRaw(event) {
            it.containsValue("type", MessageType.ANSWER.name) &&
                    it.containsValue("category", CATEGORY)
        } ?: return false
        return handle(teamRegistration)
    }

    private fun handle(answer: Answer): Boolean {
        logger.debug("Handling answer", answer)
        if (!teams.contains(answer.teamName)) {
            logger.info("new quiz team created", answer.teamName)
            teams.add(answer.teamName)
            publish(answer.teamName, answer.messageId)
        }
        return true
    }

    internal fun events(): List<Message> {
        val out = outEvents.toList()
        outEvents.clear()
        return out
    }

    internal fun shouldBeAsked(): Boolean {
        return !hasBeenAsked()
    }

    internal fun question(): Question {
        val question = Question(category = CATEGORY, question = "Register your team name")
        questionPostedId = question.messageId
        return question
    }

    private fun publish(teamName: String, answerId: String) {
        val assessment =
            Assessment(category = CATEGORY, teamName = teamName, questionId = questionPostedId!!, answerId = answerId)
        logger.debug("publishing assessment", assessment)
        outEvents.add(assessment)
    }

    private fun hasBeenAsked() = questionPostedId != null

}

