package no.nav.quizboard

import no.nav.quizrapid.Assessment
import no.nav.quizrapid.Question


class Category(question: Question) {
    internal val name = question.category
    private val questions = mutableListOf<CategoryQuestion>()

    init {
        handle(question)
    }


    fun handle(question: Question) {
        if (question.category != name) return
        if (questions.contains(question.id())) return
        questions.add(CategoryQuestion(question.id()))
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
}


private class CategoryQuestion(val id: String) {
    var failure = mutableSetOf<String>()
    var ok = mutableSetOf<String>()

    fun handle(assessment: Assessment) {
        if (assessment.questionId != id) return
        val name = assessment.teamName
        if (failed(name)) if (assessment.ok()) ok.add(name) else failure.add(name)
    }

    private fun failed(teamName: String) = teamName !in ok

    fun status(teamName: String): Status {
        if (teamName in ok) return Status.OK
        if (teamName in failure) return Status.FAILURE
        return Status.PENDING
    }

    fun teams() = failure + ok
    fun ok(teamName: String) = status(teamName) == Status.OK
}

internal fun Iterable<Category>.score(teamName: String) =
    fold(0) {sum, cat -> sum + cat.okCount(teamName) * 10 } // 10 points per ok question

internal fun Iterable<Category>.result(teamName: String) =
    map { CategoryResult(it.name, it.status(teamName), it.okCount(teamName)) }

internal fun MutableList<Category>.handle(question: Question) =
    firstOrNull { it.name == question.category }?.handle(question) ?: add(Category(question))

internal fun Iterable<Category>.handle(assessment: Assessment) = forEach { it.handle(assessment) }

internal fun Iterable<Category>.teams(): List<String> =
    fold(emptySet<String>()) { set, question -> set + question.teams() }.toList()


private fun Iterable<CategoryQuestion>.okCount(teamName: String) = fold(0) { sum, question -> if(question.ok(teamName)) sum + 1 else sum}

@JvmName("handleQuestion")
private fun Iterable<CategoryQuestion>.handle(assessment: Assessment) = forEach { it.handle(assessment) }

@JvmName("containsQuestion")
private fun Iterable<CategoryQuestion>.contains(id: String) = any { it.id == id }

@JvmName("teamsQuestion")
private fun Iterable<CategoryQuestion>.teams(): List<String> {
    return fold(emptySet<String>()) { set, question -> set + question.teams() }.toList()
}

