import java.nio.file.Paths

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
        dependsOn(clean, ":quizboard:frontend:npm_run_build")

        archiveFileName.set("app.jar")

        manifest {
            attributes["Main-Class"] = "no.nav.quizboard.QuizboardKt"
            attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(separator = " ") {
                it.name
            }
        }

        from({ Paths.get(project(":quizboard:frontend").buildDir.path) }) {
            into("static")
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
