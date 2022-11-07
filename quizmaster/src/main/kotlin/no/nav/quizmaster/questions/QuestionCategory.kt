package no.nav.quizmaster.questions

import no.nav.quizrapid.Answer
import no.nav.quizrapid.Assessment
import no.nav.quizrapid.Message
import no.nav.quizrapid.Question
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.LocalDateTime.now

abstract class QuestionCategory(
    val category: String,
    var maxCount: Int = 1,
    protected var active: Boolean = true,
    private val interval: Duration = Duration.ZERO
) {
    protected open val logger = LoggerFactory.getLogger(this.javaClass.name)
    protected val sentQuestions = mutableListOf<Question>()
    private val outEvents = mutableListOf<Assessment>()
    private var questionDelay = now()
    private val questionCounter: Int get() = sentQuestions.size
    private var answerCounter = 0
    private var correctAnswersCounter = 0

    internal fun handle(answer: Answer): Boolean {
        if (answer.category != category) return false
        if (answer.questionId !in sentQuestions.map { it.messageId }) return false
        answerCounter++
        check(answer)
        return true
    }

    internal fun handle(question: Question): Boolean {
        if (question.category != category) return false
        if (question.messageId in sentQuestions.map { it.messageId }) return false
        if(sync(question)) {
            logger.info("syncing question: ${question.id()} to category: $category")
            sentQuestions.add(question)
        }
        return true
    }

    internal abstract fun check(answer: Answer)
    internal open fun accept(answerId: String) {}

    fun questions(): List<Question> {
        if(!active) return  emptyList()
        if(now() < questionDelay) return emptyList()
        else questionDelay = now() + interval

        val capped = newQuestions().filterIndexed { i,_ ->
            (questionCounter + i < maxCount)
        }

        if (questionCounter + capped.size >= maxCount && active) {
            logger.info("category: ${this.javaClass} reached question limit = $maxCount")
            active = false
        }
        sentQuestions += capped
        return capped
    }

    protected abstract fun newQuestions(): List<Question>

    // Sync questions published by another Quizmaster to the question category
    // The QuestionCategory implementation decides if the question should be synced or discarded
    protected abstract fun sync(question: Question): Boolean

    fun events(): List<Message> {
        val out = outEvents.toList()
        correctAnswersCounter += out.count { it.isOk() }
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
        logger.debug("publishing assessment: {}", assessment.json())
        outEvents.add(assessment)
    }

    internal open fun stats(): CategoryStats {
        return CategoryStats(
            category,
            if (active) Status.ACTIVE else Status.INACTIVE,
            maxCount,
            questionCounter,
            answerCounter,
            correctAnswersCounter,
            emptyList()
        )
    }

    // TODO: burde denne brukes?
    protected fun maxCountReached() = questionCounter == maxCount

    fun activate() {
        if (active) return
        if(questionCounter >= maxCount) maxCount *= 2
        active = true
    }
}

enum class Status { ACTIVE, INACTIVE }
data class CategoryStats(
    val name: String,
    val status: Status,
    val maxCount: Int,
    val questionCount: Int,
    val answerCount: Int,
    val correctAnswerCount: Int,
    val pendingAnswers: List<PendingAnswer>
)

data class PendingAnswer(val teamName: String, val answerId: String, val answer: String)