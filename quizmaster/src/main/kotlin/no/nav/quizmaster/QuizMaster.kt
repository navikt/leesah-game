package no.nav.quizmaster

import no.nav.quizmaster.questions.*
import no.nav.quizrapid.*
import java.time.Duration


class QuizMaster : QuizParticipant {
    private val questions = listOf(
        RegisterTeam(),
        Arithmetic(Duration.ofMinutes(1), false),
        NAV(Duration.ofMinutes(5), false),
        Deduplication(Duration.ofMinutes(1), 10, false),
        Transactions(20, false, Duration.ofMinutes(1)),
        PingPong(10, false, Duration.ofMinutes(1)),
        Base64Echo(5, false, Duration.ofMinutes(1)),
        IsAPrime()
    )

    fun events(): List<String> {
        val outQuestions: List<String> = questions
            .fold(emptyList()) { list, question ->
                list + question.questions().json()
            }

        val outEvents: List<String> = questions
            .fold(emptyList()) { list, question ->
                list + question.events().json()
            }
        return outEvents + outQuestions
    }

    override fun handle(question: Question) = questions.any { it.handle(question) }

    override fun handle(answer: Answer) = questions.any { question -> question.handle(answer) }


    override fun handle(assessment: Assessment) = false

    override fun messages(): List<Message> {
        val outQuestions: List<Message> = questions
            .fold(emptyList()) { list, question ->
                list + question.questions()
            }

        val outEvents: List<Message> = questions
            .fold(emptyList()) { list, question ->
                list + question.events()
            }
        return outEvents + outQuestions
    }

    fun categories(): List<String> = questions.map { it.category }
    fun stats(category: String): CategoryStats? = questions.firstOrNull { it.category == category }?.stats()
    fun stats(): QuizStats {
        return QuizStats(Status.ACTIVE, questions.map { it.stats() })
    }

    fun activate(category: String): CategoryStats? {
        return questions.firstOrNull { it.category == category }?.apply {
            activate()
        }?.stats()
    }
}

data class QuizStats(val status: Status, val categories: List<CategoryStats>)

private fun Iterable<Message>.json() = map { it.json() }