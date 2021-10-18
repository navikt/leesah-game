package no.nav.quizrapid

class Config(
    val appName: String,
    val bootstrapServers: List<String>,
    val quizTopic: String,
    ) {

    companion object {
        fun fromEnv(): Config {
            return Config(
                System.getenv("NAIS_APP_NAME"),
                System.getenv("BOOTSTRAP_SERVERS").split(";"),
                "quiz-rapid"
            )
        }
    }
}