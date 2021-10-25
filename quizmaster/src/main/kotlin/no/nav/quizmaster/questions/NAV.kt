package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.time.LocalDateTime

class NAV(private val frequency: Duration): QuestionCategory("NAV", 2, false) {
    private var nextQuestion = LocalDateTime.now() + frequency
    private val publishedQuestions = mutableMapOf<String, String>()
    private var questionIndex = 0
    private val navQuestions = listOf(
            navQuestion(
                "På hvilken nettside finner man informasjon om rekruttering til NAV IT?",
                "detsombetyrnoe"
            ),
            navQuestion(
                "Hva heter applikasjonsplattformen til NAV?",
                "nais"
            )
        )

    override fun check(answer: Answer) {
        publishedQuestions[answer.questionId]?.checkAnswer(answer) ?: run {
            logger.warn("answer = $answer contains invalid data = ${answer.answer}")
        }
    }

    // TODO: accept a list of correct answers, evt. a function (String) -> Boolean
    private fun String.checkAnswer(answer: Answer){
        val validation = answer.answer
            .replace("\\s".toRegex(), "")
            .contains(this, true)
        validation.publish(answer.teamName, answer.questionId, answer.messageId)
    }

    private fun storeQuestion(newQuestion: Question, fasit: String) {
        publishedQuestions[newQuestion.messageId] = fasit
    }

    override fun newQuestions(): List<Question> {
        if (!(LocalDateTime.now() > nextQuestion && active)) {
            return emptyList()
        }
        val q = getNextQuestion().also {
            nextQuestion = LocalDateTime.now() + frequency
        }
        return if (q == null) emptyList() else listOf(q)
    }

    private fun getNextQuestion() : Question? {
        return if (questionIndex > navQuestions.lastIndex) null
        else {
            navQuestions.get(questionIndex).also {
                questionIndex++
            }
        }

    }


    private fun navQuestion(spørsmål : String, fasit : String) : Question {
        val question = Question(
            category = category,
            question = spørsmål
        )
        storeQuestion(question, fasit)
        return question
    }
}