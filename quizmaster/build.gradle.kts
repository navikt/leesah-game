val ktorVersion = "1.6.4"
val kafkaVersion = "2.8.0"
val junitJupiterVersion = "5.8.1"

plugins {
    kotlin("jvm")
}

group = "no.nav"
version = "0.0.1"


dependencies {
    implementation(project(":quizrapid"))
    implementation("io.ktor:ktor-jackson:1.6.4")
}

tasks {
    jar {
        mustRunAfter(clean)

        archiveFileName.set("app.jar")

        manifest {
            attributes["Main-Class"] = "no.nav.quizmaster.QuizmasterServerKt"
            attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(separator = " ") {
                it.name
            }
        }
    }
}
