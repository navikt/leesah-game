package no.nav.quizmaster.questions

import io.ktor.util.*
import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.util.*
import kotlin.random.Random

class Base64Echo(maxCount: Int = 1, active: Boolean = true, interval: Duration = Duration.ZERO) :
    QuestionCategory("base64", maxCount, active, interval) {

    val words = listOf("Python", "Golang", "Lisp", "Simula")
    private val publishedQuestions = mutableMapOf<String, String>()

    override fun check(answer: Answer) {
        if (answer.questionId !in publishedQuestions.keys) return
        (publishedQuestions[answer.questionId] == answer.answer).publish(
            answer.teamName,
            answer.questionId,
            answer.messageId
        )
    }

    override fun newQuestions(): List<Question> {
        if (!active) {
            return emptyList()
        }
        val fasit = words[Random.nextInt(words.size)]
        val newQuestion = Question(
            category = category,
            question = "echo ${Base64.getEncoder().encodeToString(fasit.toByteArray())}"
        )
        storeQuestion(newQuestion, fasit)
        return listOf(newQuestion)
    }

    private fun storeQuestion(newQuestion: Question, fasit: String) {
        publishedQuestions[newQuestion.messageId] = fasit
    }
}