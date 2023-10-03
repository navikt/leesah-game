val ktorVersion = "2.3.4"
val kafkaVersion = "3.5.1"
val junitJupiterVersion = "5.10.0"
val kotlinVersion = "1.9.10"
val jacksonVersion = "2.15.2"
val slf4jApiVersion = "1.7.32"
val logbackVersion = "1.4.11"
val logstashEncoderVersion = "7.4"
val awaitilityVersion = "4.1.0"
val kafkaEmbeddedEnvVersion = "3.2.3"
val micrometerVersion = "1.7.4"

plugins {
    kotlin("jvm") version "1.9.10"
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
        compileKotlin {
            kotlinOptions.jvmTarget = "17"
        }

        compileTestKotlin {
            kotlinOptions.jvmTarget = "17"
        }
       test {
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
        implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
        implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
        implementation("com.fasterxml.jackson.core:jackson-core:$jacksonVersion")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")


        implementation("org.slf4j:slf4j-api:$slf4jApiVersion")
        implementation("ch.qos.logback:logback-classic:$logbackVersion")
        implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")


        api("io.ktor:ktor-server-metrics-micrometer:$ktorVersion")
        api("io.micrometer:micrometer-registry-prometheus:$micrometerVersion")

        testImplementation("no.nav:kafka-embedded-env:$kafkaEmbeddedEnvVersion")
        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
        testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
        testImplementation("org.awaitility:awaitility:$awaitilityVersion")
    }
}