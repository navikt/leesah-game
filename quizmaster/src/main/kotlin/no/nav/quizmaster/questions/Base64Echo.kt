package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.util.*
import kotlin.random.Random

class Base64Echo(maxCount: Int = 1, active: Boolean = true, interval: Duration = Duration.ZERO) :
    QuestionCategory("base64", maxCount, active, interval) {

    val words = listOf("Python", "Golang", "Lisp", "Simula")
    private val fasit = mutableMapOf<String, String>()

    override fun check(answer: Answer) {
        if (answer.questionId !in fasit.keys) return
            (fasit[answer.questionId] == answer.answer).publish(
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

    override fun sync(question: Question): Boolean {
        val answer = decode(question)
        if(answer in words) {
            storeQuestion(question, answer)
            return true
        }
        return false
    }

    private fun storeQuestion(newQuestion: Question, fasit: String) {
        this.fasit[newQuestion.messageId] = fasit
    }

    private fun decode(question: Question): String =
        String(Base64.getDecoder().decode(question.question.split(" ")[1]))
}
