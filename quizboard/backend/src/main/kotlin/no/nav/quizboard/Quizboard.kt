package no.nav.quizboard

import no.nav.quizrapid.*

class Quizboard: QuizParticipant {

    private val categories = mutableListOf<Category>()

    override fun handle(question: Question): Boolean {
        categories.handle(question)
        return true
    }

    override fun handle(answer: Answer) = true

    override fun handle(assessment: Assessment): Boolean {
        categories.handle(assessment)
        return true
    }

    override fun messages(): List<Message> = emptyList()

    fun result(): BoardResult {
        val teams = categories.teams()
        return BoardResult(teams.map { TeamResult(it, 100, categories.result(it)) })
    }


}

data class BoardResult(
    val board: List<TeamResult>
)

data class TeamResult(
    val name: String,
    val score: Int,
    val categoryResult: List<CategoryResult>
)

data class CategoryResult(
    val name: String,
    val status: Status,
    val okCount: Int
)


enum class Status {
    OK, PENDING, FAILURE
}

