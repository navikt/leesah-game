package no.nav.quizboard

import no.nav.quizrapid.*

class Quizboard: QuizParticipant {

    private val categories = mutableListOf<Category>()

    internal val quizTeams = mutableMapOf<String, Int>()

    override fun handle(question: Question): Boolean {
        categories.handle(question)
        return true
    }

    override fun handle(answer: Answer) = true

    override fun handle(assessment: Assessment): Boolean {
        categories.handle(assessment)
        return true
    }

    fun result(): List<TeamResult> {
        val teams = categories.teams()
        return teams.map { TeamResult(it, 100, categories.result(it)) }
    }

    override fun messages(): List<Message> = emptyList()

}

data class TeamResult(
    val name: String,
    val score: Int,
    val categoryResult: List<CategoryResult>
)

data class CategoryResult(
    val name: String,
    val status: Status
)


enum class Status {
    OK, PENDING, FAILURE
}

