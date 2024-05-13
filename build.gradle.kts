val ktorVersion = "1.6.8"
val kafkaVersion = "3.6.0"
val junitJupiterVersion = "5.10.2"
val kotlinVersion = "1.9.24"
val jacksonVersion = "2.17.1"
val slf4jApiVersion = "2.0.13"
val logbackClassicVersion = "1.5.6"
val logbackEncoderVersion = "7.4"
val prometheusVerison = "1.12.6"
val kafkaEmbeddedEnvVersion = "3.2.8"
val awaitilityVerison = "4.2.1"

plugins {
    kotlin("jvm") version "1.9.24"
}
allprojects {
    repositories {
        mavenCentral()
        maven("https://packages.confluent.io/maven/")
        maven("https://jitpack.io")
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    tasks {
        withType<Test> {
            useJUnitPlatform()
            testLogging {
                events("skipped", "failed")
            }
        }
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

        implementation("org.apache.kafka:kafka-clients:$kafkaVersion")
        implementation("io.ktor:ktor-server-core:$ktorVersion")
        implementation("io.ktor:ktor-server-cio:$ktorVersion")
        implementation("io.ktor:ktor-client-cio:$ktorVersion")
        implementation("io.ktor:ktor-jackson:$ktorVersion")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
        implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")


        implementation("org.slf4j:slf4j-api:$slf4jApiVersion")
        implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")
        implementation("net.logstash.logback:logstash-logback-encoder:$logbackEncoderVersion")


        api("io.ktor:ktor-metrics-micrometer:$ktorVersion")
        api("io.micrometer:micrometer-registry-prometheus:$prometheusVerison")

        testImplementation("no.nav:kafka-embedded-env:$kafkaEmbeddedEnvVersion")
        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
        testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
        testImplementation("org.awaitility:awaitility:$awaitilityVerison")
    }
}