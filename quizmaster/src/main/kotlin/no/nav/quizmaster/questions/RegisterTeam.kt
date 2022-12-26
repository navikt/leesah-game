package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class RegisterTeam(active: Boolean) : QuestionCategory(category = "team-registration", active = active) {

    override val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)
    private val teams = mutableListOf<String>()
    private val fasit = mutableMapOf<String, Int>()

    fun newTeam(team: Answer) {
        if (team.teamName !in teams && team.answer.length == 6) {
            logger.info("New quiz team created with teamname ${team.teamName}")
            teams.add(team.teamName)
            true.publish(team.teamName, sentQuestions[0].id(), team.messageId)
        } else {
            logger.debug("Incorrect team registration: hex = #${team.json()}")
        }
    }

    override fun check(answer: Answer) {
        if (answer.answer.length == 6) {
            try {
                newTeam(answer)
                fasit[answer.questionId]?.checkAnswer(answer)
                logger.info("New team hex color #${answer.answer}")
            } catch (e: NumberFormatException) {
                logger.warn("answer = $answer contains invalid data = ${answer.answer}")
                logger.debug(e.toString())
            }
        } else {
            logger.debug("Incorrect answer = $answer must be a 6-character hex-code, not '${answer.answer}'")
        }
    }

    private fun Int.checkAnswer(answer: Answer) {
        (this == answer.answer.toInt()).publish(answer.teamName, answer.questionId, answer.messageId)
    }

    override fun newQuestions(): List<Question> {
        return if (active) {
            val question = Question(
                category = category,
                question = "Choose a 6-character hex-code to represent your team (without #)."
            )
            listOf(question)
        } else emptyList()
    }

    override fun sync(question: Question) = true
}

