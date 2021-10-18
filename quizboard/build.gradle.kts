plugins {
    kotlin("jvm")
}

group = "no.nav"
version = "0.0.1"

dependencies {
    implementation(project(":quizrapid"))
}

tasks {
    jar {
        mustRunAfter(clean)

        archiveFileName.set("app.jar")

        manifest {
            attributes["Main-Class"] = "no.nav.quizboard.QuizboardKt"
            attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(separator = " ") {
                it.name
            }
        }
    }
}
