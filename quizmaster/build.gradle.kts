import java.nio.file.Paths
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    implementation(kotlin("stdlib-jdk8"))

}

tasks {
    jar {
        dependsOn(":quizmaster:adminpanel:npm_run_build")

        archiveFileName.set("app.jar")

        manifest {
            attributes["Main-Class"] = "no.nav.quizmaster.QuizmasterServerKt"
            attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(separator = " ") {
                it.name
            }
        }

        from({ Paths.get(project(":quizmaster:adminpanel").buildDir.path) }) {
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
repositories {
    mavenCentral()
}
val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}