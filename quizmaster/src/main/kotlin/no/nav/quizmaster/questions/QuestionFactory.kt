package no.nav.quizmaster.questions

import no.nav.quizmaster.*
import no.nav.quizrapid.*
import org.slf4j.LoggerFactory

abstract class QuestionFactory(val category: String, private val maxCount: Int = 1) {
    protected open val logger = LoggerFactory.getLogger(this.javaClass.name)
    private val outEvents = mutableListOf<Assessment>()
    private var questionCounter = 0
    protected var active = true


    internal abstract fun handle(answer: Answer)

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

}