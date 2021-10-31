package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Question
import java.time.Duration
import java.time.LocalDateTime

class Transactions(maxCount : Int = 20, active: Boolean = false):
    QuestionCategory("transactions", maxCount, active) {
    private var nextQuestion = LocalDateTime.now()
    private val publishedQuestions = mutableMapOf<String, Int>()

    override fun check(answer: Answer) {
        publishedQuestions[answer.questionId]?.checkAnswer(answer) ?: run {
            logger.warn("answer = $answer contains invalid data = ${answer.answer}")
        }
    }

    private fun storeQuestion(newQuestion: Question, fasit: Int) {
        publishedQuestions[newQuestion.messageId] = fasit
    }

    override fun newQuestions(): List<Question> {
        if (!(LocalDateTime.now() > nextQuestion && active)) {
            return emptyList()
        }

        val transactions = getTransactions()
        val questions = transactions.map {
            Question(
                category = category,
                question = it.toString()
            )
        } as MutableList<Question>
        val transactionSum = transactions.sum()
        questions.forEach{ storeQuestion(it, transactionSum) }
        return questions
    }


    private fun Int.checkAnswer(answer: Answer) {
        // hacky fix to publish a correct assessment for every transaction
        // since QuestionCategory requires one assessment per question
        val isAnswerCorrect = (this == answer.answer.toInt())
        publishedQuestions.keys.forEach{
           isAnswerCorrect.publish(answer.teamName, it, answer.messageId)
        }
    }

    private fun getTransactions() : List<Int> {
        val transactions = mutableListOf<Int>()
        repeat(maxCount){
            transactions.add((-1000000..1000000).random())
        }
        return transactions
    }
}