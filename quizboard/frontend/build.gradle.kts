plugins {
    base
    id("com.github.node-gradle.node") version "7.0.0"
}

tasks.assemble {
    dependsOn("npm_run_build")
}

project.layout.buildDirectory.dir("dist").get().asFile