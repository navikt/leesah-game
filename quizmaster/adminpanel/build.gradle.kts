plugins {
    base
    id("com.github.node-gradle.node") version "7.0.2"
}

tasks.assemble {
    dependsOn("npm_run_build")
}

project.buildDir = File("dist")