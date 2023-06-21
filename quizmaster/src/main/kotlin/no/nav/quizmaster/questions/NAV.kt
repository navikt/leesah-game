package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.time.LocalDateTime

class NAV(private val frequency: Duration, active: Boolean = false) : QuestionCategory("NAV", 7, active) {
    private var nextQuestionAt = LocalDateTime.now()
    private val fasit = mutableMapOf<String, List<String>>()
    private var questionIndex = 0
    private val navQuestions: Map<String, List<String>> = mapOf(
        Pair(
            "På hvilken nettside finner man informasjon om rekruttering til NAV IT?",
            listOf("det som betyr noe")
        ),
        Pair(
            "Hva heter applikasjonsplattformen til NAV?",
            listOf("nais", "NAVs Application Infrastructure Service", "NAV Application Infrastructure Service")
        ),
        Pair(
            "Hva heter NAV-direktøren?",
            listOf("Hans Christian Holte")
        ),
        Pair(
            "Hvor har vi kontor?",
            listOf("Helsfyr", "Oslo")
        ),
        Pair(
            "Hva heter designsystemet vårt?",
            listOf("Aksel")
        ),
        Pair(
            "Hvor mye er 1G per 1. mai 2023?",
            listOf("118620", "118620 kr", "118620 kroner")
        ),
    )

    override fun check(answer: Answer) {
        fasit[answer.questionId]?.checkAnswer(answer) ?: run {
            logger.warn("answer = $answer contains invalid data = ${answer.answer}")
        }
    }

    // TODO: accept a list of correct answers, evt. a function (String) -> Boolean
    private fun List<String>.checkAnswer(answer: Answer) {
        this.forEach {
            if (answer.answer.replace("\\s".toRegex(), "").contains(it.replace("\\s".toRegex(), ""), true)) {
                true.publish(answer.teamName, answer.questionId, answer.messageId)
                return
            }
        }
        false.publish(answer.teamName, answer.questionId, answer.messageId)
    }

    private fun storeFasit(newQuestion: Question, fasit: List<String>) {
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
        val fasit: List<String>? = navQuestions[question.question]
        if (fasit != null) {
            storeFasit(question, fasit)
            return true
        }
        return false
    }
}
