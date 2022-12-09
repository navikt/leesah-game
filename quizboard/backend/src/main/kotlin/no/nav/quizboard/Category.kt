package no.nav.quizboard

import no.nav.quizrapid.Assessment
import no.nav.quizrapid.Question
import org.slf4j.LoggerFactory
import java.lang.Integer.max
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

private val logger = LoggerFactory.getLogger("category-questions")

class Category(question: Question) {
    internal val name = question.category
    private val questions = mutableListOf<CategoryQuestion>()

    init {
        handle(question)
    }


    fun handle(question: Question) {
        if (question.category != name) return
        if (questions.contains(question.id())) return
        questions.add(CategoryQuestion(question.id(), question.created()))
    }

    fun handle(assessment: Assessment) {
        questions.handle(assessment)
    }

    fun status(teamName: String): Status {
        if (questions.any { it.status(teamName) == Status.FAILURE }) return Status.FAILURE
        if (questions.any { it.status(teamName) == Status.PENDING }) return Status.PENDING
        return Status.OK
    }

    fun teams() = questions.teams()
    fun okCount(teamName: String) = questions.okCount(teamName)

    fun score(teamName: String) = questions.score(teamName)

}


private class CategoryQuestion(val id: String, val received: LocalDateTime) {


    data class OkAssessment(val teamName: String, val acceptedAt: LocalDateTime)

    var failure = mutableSetOf<String>()
    var ok = mutableSetOf<OkAssessment>()


    /**
     * Handle an Assessment of a question instance
     * Best Assessment (oldest) is kept for maximum score
     * Failures are voided by later successful assessments
     */
    fun handle(assessment: Assessment) {
        if (assessment.questionId != id) return
        val name = assessment.teamName
        if (!assessment.isOk()){
            failure.add(name)
            logger.info("received failing assessment: category: {} qId: {} team: {} qReceived: {}",
                assessment.category,
                assessment.questionId,
                assessment.teamName,
                received)
            return
        }

        failure.remove(name) // An OK assessment voids a failure

        if (assessment.teamName in ok.map { it.teamName }) {
            logger.info(
                "ok assessment received but already received an earlier ok assessment: category: {} qId: {} team: {}",
                assessment.category,
                assessment.questionId,
                assessment.teamName
            )
            return
        }
        val okAssessment = OkAssessment(name, assessment.created())
        logger.info(
            "received ok assessment: category: {} qId: {} team: {} qReceived: {} aReceived: {} timeUsed: {}",
            assessment.category,
            assessment.questionId,
            assessment.teamName,
            received,
            okAssessment.acceptedAt,
            ChronoUnit.MINUTES.between(this.received, okAssessment.acceptedAt)
        )
        ok.add(okAssessment)
    }

    private fun failed(teamName: String) = teamName !in ok.map { it.teamName }

    fun status(teamName: String): Status {
        if (teamName in failure) return Status.FAILURE
        if (teamName in ok.map { it.teamName }) return Status.OK
        return Status.PENDING
    }

    fun teams() = failure + ok.map { it.teamName }
    fun ok(teamName: String) = status(teamName) == Status.OK
    fun score(teamName: String): Int {
        if(status(teamName) != Status.OK) return 0
        val assessment = ok.first { it.teamName == teamName }
        val timeUsed = ChronoUnit.MINUTES.between(this.received, assessment.acceptedAt)
        return scoreAlgorithm(timeUsed)
    }

    // base score of 5 for right answer plus between 0 and 5 extra point weighted by time used to solve the question
    private fun scoreAlgorithm(time: Long) = 5 + max((5 -((time.toDouble() / 60.0) * 5.0).toInt()), 0)
}

internal fun Iterable<Category>.score(teamName: String) =
    fold(0) { sum, cat -> sum + cat.score(teamName)} // 10 points per ok question


internal fun Iterable<Category>.result(teamName: String) =
    map { CategoryResult(it.name, it.status(teamName), it.okCount(teamName)) }

internal fun MutableList<Category>.handle(question: Question) =
    firstOrNull { it.name == question.category }?.handle(question) ?: add(Category(question))

internal fun Iterable<Category>.handle(assessment: Assessment) = forEach { it.handle(assessment) }

internal fun Iterable<Category>.teams(): List<String> =
    fold(emptySet<String>()) { set, question -> set + question.teams() }.toList()


private fun Iterable<CategoryQuestion>.okCount(teamName: String) =
    fold(0) { sum, question -> if (question.ok(teamName)) sum + 1 else sum }

@JvmName("scoreCategoryQuestion")
private fun Iterable<CategoryQuestion>.score(teamName: String) =
    fold(0) { sum, question -> if (question.ok(teamName)) sum + question.score(teamName) else sum }

@JvmName("handleQuestion")
private fun Iterable<CategoryQuestion>.handle(assessment: Assessment) = forEach { it.handle(assessment) }

@JvmName("containsQuestion")
private fun Iterable<CategoryQuestion>.contains(id: String) = any { it.id == id }

@JvmName("teamsQuestion")
private fun Iterable<CategoryQuestion>.teams(): List<String> {
    return fold(emptySet<String>()) { set, question -> set + question.teams() }.toList()
}

