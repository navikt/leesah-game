package no.nav.quizmaster.no.nav.quizmaster

class Config(
    val bootstrapServers: List<String>,
    val quizTopic: String,
    ) {

    companion object {
        fun fromEnv(): Config {
            return Config(
                System.getenv("BOOTSTRAP_SERVERS").split(";"),
                "quiz-rapid"
            )
        }
    }
}