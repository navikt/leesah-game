package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.time.LocalDateTime

class NAV(private val frequency: Duration, active: Boolean = false) : QuestionCategory("NAV", 2, active) {
    private var nextQuestionAt = LocalDateTime.now()
    private val fasit = mutableMapOf<String, String>()
    private var questionIndex = 0
    private val navQuestions = mapOf(
        Pair(
            "På hvilken nettside finner man informasjon om rekruttering til NAV IT?",
            "detsombetyrnoe"
        ),
        Pair(
            "Hva heter applikasjonsplattformen til NAV?",
            "nais"
        ),
        Pair(
            "Hva står NAV for?",
            "nav"
        ),
        Pair(
            "Hva heter NAV-direktøren?",
            "Hans Christian Holte"
        )
    )

    override fun check(answer: Answer) {
        fasit[answer.questionId]?.checkAnswer(answer) ?: run {
            logger.warn("answer = $answer contains invalid data = ${answer.answer}")
        }
    }

    // TODO: accept a list of correct answers, evt. a function (String) -> Boolean
    private fun String.checkAnswer(answer: Answer) {
        val validation = answer.answer
            .replace("\\s".toRegex(), "")
            .contains(this, true)
        validation.publish(answer.teamName, answer.questionId, answer.messageId)
    }

    private fun storeFasit(newQuestion: Question, fasit: String) {
        this.fasit[newQuestion.messageId] = fasit
    }

    override fun newQuestions(): List<Question> {
        if (!(LocalDateTime.now() > nextQuestionAt && active)) {
            return emptyList()
        }
        val q = getNextQuestion().also {
            nextQuestionAt = LocalDateTime.now() + frequency
        }
        return if (q == null) emptyList() else listOf(q)
    }

    private fun getNextQuestion(): Question? {
        return if (questionIndex > navQuestions.size - 1) null
        else {
            newQuestion(navQuestions.keys.toList()[questionIndex]).also {
                storeFasit(it, navQuestions[it.question]!!)
                questionIndex++
            }
        }

    }

    private fun newQuestion(spørsmål: String): Question = Question(
        category = category,
        question = spørsmål
    )


    override fun sync(question: Question): Boolean {
        val fasit = navQuestions[question.question]
        if(fasit != null) {
            storeFasit(question, fasit)
            return true
        }
        return false
    }
}
