package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration

class IsAPrime(maxCount: Int = 10, active: Boolean = false, interval: Duration = Duration.ofMinutes(1)) :
    QuestionCategory("is-a-prime", maxCount, active, interval) {

    private val numbers = mapOf(2 to true, 3 to true, 4 to false, 5 to true, 6 to false, 7 to true, 8 to false, 11 to true, 13 to true, 17 to true, 18 to false, 19 to true, 23 to true, 30 to false, 31 to true, 37 to true, 40 to false, 41 to true, 43 to true, 47 to true, 50 to false, 53 to true, 59 to true, 61 to true, 67 to true, 71 to true, 73 to true, 79 to true, 83 to true, 89 to true, 97 to true, 100 to false)
    val fasit = mutableMapOf<String, Boolean>()

    companion object {
        internal fun questionNumber(question: Question) = question.question.split("?")[1].removePrefix(" ").toInt()
    }

    override fun check(answer: Answer) {
        fasit[answer.questionId]?.let {
            it && answer.answer.lowercase() == "true" || !it && answer.answer.lowercase() == "false"
        }?.publish(answer.teamName, answer.questionId, answer.messageId)
    }

    override fun newQuestions(): List<Question> {
        if (!active) {
            return emptyList()
        }
        val number = kotlin.random.Random.nextInt(numbers.size).let { numbers.entries.toList()[it] }

        val newQuestion = Question(
            category = category,
            question = "Er det et primtall? ${number.key} (svar på format \"true\" eller \"false\". Husk fnuttene!)"
        )
        fasit[newQuestion.id()] = number.value
        return listOf(newQuestion)
    }

    override fun sync(question: Question): Boolean {
        val questionNumber = questionNumber(question)
        if(questionNumber in numbers.keys) {
            fasit[question.id()] = numbers[questionNumber]!!
            return true
        }
        return false
    }

}
