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
            logger.info("Nytt team opprettet med teamnavn ${team.teamName}")
            teams.add(team.teamName)
            true.publish(team.teamName, sentQuestions[0].id(), team.messageId)
        } else {
            logger.debug("Ugyldig team registrering: hex = #${team.json()}")
        }
    }

    override fun check(answer: Answer) {
        if (answer.answer.length == 6) {
            try {
                newTeam(answer)
                fasit[answer.questionId]?.checkAnswer(answer)
                logger.info("Ny hex-farge #${answer.answer}")
            } catch (e: NumberFormatException) {
                logger.warn("Svar = $answer inneholder ugyldig data = ${answer.answer}")
                logger.debug(e.toString())
            }
        } else {
            logger.debug("Feil svar = $answer må være en hex-kode på 6 tegn, ikke '${answer.answer}'")
        }
    }

    private fun Int.checkAnswer(answer: Answer) {
        (this == answer.answer.toInt()).publish(answer.teamName, answer.questionId, answer.messageId)
    }

    override fun newQuestions(): List<Question> {
        return if (active) {
            val question = Question(
                category = category,
                question = "Velg en hex-code med 6 tegn (uten #) for å representere ditt team."
            )
            listOf(question)
        } else emptyList()
    }

    override fun sync(question: Question) = true
}

