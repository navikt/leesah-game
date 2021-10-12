package no.nav.quizmaster

class Config(
    val bootstrapServers: List<String> ) {

    companion object {
        fun fromEnv(): Config {
            return Config(
                System.getenv("BOOTSTRAP_SERVERS").split(";")
            )
        }
    }
}