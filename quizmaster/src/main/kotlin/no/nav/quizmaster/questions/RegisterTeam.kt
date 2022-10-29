package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class RegisterTeam : QuestionCategory("team-registration") {

    override val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)
    private val teams = mutableListOf<String>()

    override fun check(answer: Answer) {
        logger.debug("Handling answer: {}", answer)
        if (answer.answer !in teams) {
            logger.info("new quiz team created: team = ${answer.answer}")
            teams.add(answer.answer)
            true.publish(answer.answer, sentQuestions[0].id(), answer.messageId)
        } else {
            logger.debug("Incorrect team registration: answer = ${answer.json()}")
        }
    }

    override fun newQuestions(): List<Question> {

        return if (active) {
            val question = Question(
                category = category,
                question = "Register a new team"
            )
            listOf(question)
        } else emptyList()
    }
}

