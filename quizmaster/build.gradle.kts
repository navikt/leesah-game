val ktorVersion = "1.6.4"
val kafkaVersion = "2.8.0"
val junitJupiterVersion = "5.8.1"

plugins {
    application
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
            attributes["Main-Class"] = "no.nav.quizmaster.QuizmasterServerKt"
            attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(separator = " ") {
                it.name
            }
        }

        doLast {
            configurations.runtimeClasspath.get()
                .filter { it.name != "app.jar" }
                .forEach {
                    val file = File("$buildDir/libs/${it.name}")
                    if (!file.exists())
                        it.copyTo(file)
                }
        }
    }
}
