package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class RegisterTeam : QuestionCategory("team-registration") {

    override val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)
    private var questionPostedId: String? = null
    private val teams = mutableListOf<String>()

    override fun check(answer: Answer) {
        logger.debug("Handling answer", answer)
        if (questionPostedId != null && questionPostedId == answer.questionId && answer.answer !in teams) {
            logger.info("new quiz team created: team = ${answer.answer}")
            teams.add(answer.answer)
            true.publish(answer.answer, questionPostedId!!, answer.messageId)
        } else {
            logger.warn("Incorrect team registration: answer = ${answer.json()}")
        }
    }

    override fun newQuestions(): List<Question> {

        return if (active) {
            val question = Question(
                category = category,
                question = "Register new team, template = {\"messageId\":\"<INSERT NEW UUID>\", \"questionId\": \"<INSERT QUESTION ID>\",\"type\":\"ANSWER\",\"category\":\"team-registration\",\"teamName\":  \"\", \"questionId\": \"\", \"answer\": \"<INSERT TEAM NAME>\"}"
            )
            questionPostedId = question.messageId
            listOf(question)
        } else emptyList()
    }
}

