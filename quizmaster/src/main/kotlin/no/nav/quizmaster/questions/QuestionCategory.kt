package no.nav.quizmaster.questions

import no.nav.quizmaster.*
import no.nav.quizrapid.*
import org.slf4j.LoggerFactory

abstract class QuestionCategory(val category: String, private val maxCount: Int = 1) {
    protected open val logger = LoggerFactory.getLogger(this.javaClass.name)
    private val outEvents = mutableListOf<Assessment>()
    private var questionCounter = 0
    private var answerCounter = 0
    private var correctAnswersCounter = 0
    protected var active = true


    internal fun handle(answer: Answer): Boolean {
        if(answer.category != category) return false
        answerCounter++
        check(answer)
        return true
    }

    internal abstract fun check(answer: Answer)

    fun questions(): List<Question> {
        val capped = newQuestions().filter {
            (questionCounter < maxCount).also { questionCounter++ }
        }

        if(questionCounter >= maxCount && active) {
            logger.info("${this.javaClass} reached question limit = $maxCount")
            active = false
        }
        return capped
    }

    protected abstract fun newQuestions(): List<Question>

    fun events(): List<Message> {
        val out = outEvents.toList()
        correctAnswersCounter += out.count { it.ok() }
        outEvents.clear()
        return out
    }

    protected fun Boolean.publish(teamName: String, questionId: String, answerId: String) {
        val builder = if (this) Assessment::correct else Assessment::wrong
        val assessment = builder(
            category,
            teamName,
            questionId,
            answerId
        )
        logger.debug("publishing assessment", assessment)
        outEvents.add(assessment)
    }

    internal fun stats(): Stats {
        return Stats(if(active) Status.ACTIVE else Status.INACTIVE, questionCounter, answerCounter, correctAnswersCounter)
    }
}

enum class Status{ACTIVE, INACTIVE}
data class Stats(val status: Status, val questionCount: Int, val answerCount: Int, val correctAnswerCount: Int)