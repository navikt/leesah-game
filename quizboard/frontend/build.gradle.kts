plugins {
    base
    id("com.github.node-gradle.node") version "3.0.1"
}

tasks.assemble {
    dependsOn("npm_run_build")
}

project.buildDir = File("dist")