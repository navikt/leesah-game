package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import org.slf4j.Logger
import org.slf4j.LoggerFactory


class RegisterTeam(active: Boolean) : QuestionCategory(category = "team-registration", active = active) {

    override val logger: Logger = LoggerFactory.getLogger(this.javaClass.name)
    private val teams = mutableListOf<String>()
    private val fasit = mutableMapOf<String, Int>()
    private val lengde = setOf(4,7)

    fun newTeam(team: Answer) {
        if (team.teamName.contains(" ")) {
            team.teamName.replace(" ", "_")
            logger.debug("Teamnavnet inneholdt mellomrom: Navn = ${team.teamName}")
        }
        if (team.answer.startsWith("#") && team.answer.length in lengde) {
            if (team.teamName !in teams && team.teamName.length<=30) {
                logger.info("Nytt team opprettet med teamnavn ${team.teamName}")
                teams.add(team.teamName)
                logHemmeligNøkkel(team.teamName)
                true.publish(team.teamName, sentQuestions[0].id(), team.messageId)
            } else {
                logger.debug("Ugyldig team registrering: hex = #${team.json()}")
            }
        } else {
            logger.debug("Hex koden er ikke riktig: hex = ${team.answer}")
        }

    }

    private fun logHemmeligNøkkel(teamName: String) {
        val nøkkel = LoggOrd.loggOrd.random()
        logger.info("Den hemmelige nøkkelen for $teamName er ditt $nøkkel! 🎉🎉🎉")
    }

    override fun check(answer: Answer) {
        if (checkHex(answer.answer)) {
            try {
                newTeam(answer)
                fasit[answer.questionId]?.checkAnswer(answer)
                logger.info("Ny hex-farge #${answer.answer}")
            } catch (e: NumberFormatException) {
                logger.warn("Svar = $answer inneholder ugyldig data = ${answer.answer}")
                logger.debug(e.toString())
            }
        } else {
            logger.debug("Feil svar = {} må være en hex-kode på 6 tegn, ikke '{}'", answer, answer.answer)
        }
    }

    fun checkHex(answer: String): Boolean {
        val hexPattern = "^#?[0-9a-fA-F]{6,7}$".toRegex()
        return answer.matches(hexPattern)
    }

    private fun Int.checkAnswer(answer: Answer) {
        (this == answer.answer.toInt()).publish(answer.teamName, answer.questionId, answer.messageId)
    }

    override fun newQuestions(): List<Question> {
        return if (active) {
            val question = Question(
                category = category,
                question = "Velg en hex-code med 6 tegn for å representere ditt team. Eksempel: #FFFFFF"
            )
            listOf(question)
        } else emptyList()
    }

    override fun sync(question: Question) = true
}

