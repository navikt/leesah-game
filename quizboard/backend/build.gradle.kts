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
        dependsOn(":quizboard:frontend:npm_run_build")

        archiveFileName.set("app.jar")

        manifest {
            attributes["Main-Class"] = "no.nav.quizboard.AppKt"
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
    tasks {
        compileKotlin {
            kotlinOptions.jvmTarget = "17"
        }

        compileTestKotlin {
            kotlinOptions.jvmTarget = "17"
        }
        withType<Test> {
            useJUnitPlatform()
            testLogging {
                events("skipped", "failed")
            }
        }
    }
}
