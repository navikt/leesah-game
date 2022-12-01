package no.nav.quizmaster

import no.nav.quizmaster.questions.*
import no.nav.quizrapid.*
import java.time.Duration


class QuizMaster : QuizParticipant {
    private val questions = listOf(
        RegisterTeam(false),
        Arithmetic(Duration.ofMinutes(1), false),
        NAV(Duration.ofMinutes(5), false),
        Deduplication(Duration.ofMinutes(1), 10, false),
        Transactions(20, false, Duration.ofMinutes(1)),
        PingPong(10, false, Duration.ofMinutes(1)),
        Base64Echo(5, false, Duration.ofMinutes(1)),
        IsAPrime(maxCount = 10, interval = Duration.ofMinutes(1)),
        Grunnbeløp(maxCount = 10, interval = Duration.ofMinutes(1)),
        PendingQuestion("make-ingress", question = "[NAIS Oppgave] Lag en NAIS ingress for appen din i formatet: <app navn>.dev.intern.nav.no. Send link til ingress som svar "),
        PendingQuestion("check-app-log", question = "[NAIS Oppgave] Søk opp applikasjon loggen for /hello endpunktet for din app. Send short link til søket som svar."),
        PendingQuestion("make-grafana-board", question = "[NAIS Oppgave] Lag et Grafana board for applikasjonen. Boardet skal inneholder Counters for  /hello endepunktet og antall Kafka meldinger applikasjonen har mottatt. Send link til boardet som svar."),
        PendingQuestion("make-alert", question = "[NAIS Oppgave] Lag en Alert for applikasjonen. Alarmen skal skrive en Slack melding i quiz-kanalen når /hello endepunktet har mottatt akkurat 10 kall. Send link til slack-meldingen som svar."),
        PendingQuestion("setup-wonderwall", question = "[NAIS Oppgave] Sett opp Wonderwall OIDC Autentisering over /secure endepunktet. Send link til /secure endepunktet på din app som svar.")
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

    fun accept(category: String, answerId: String): CategoryStats? {
        return questions.firstOrNull { it.category == category }?.apply {
           accept(answerId)
        }?.stats()
    }
}

data class QuizStats(val status: Status, val categories: List<CategoryStats>)

private fun Iterable<Message>.json() = map { it.json() }