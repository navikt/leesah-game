val ktorVersion = "1.6.4"
val kafkaVersion = "2.8.0"
val junitJupiterVersion = "5.8.1"
val kotlinVersion = "1.6.20"


plugins {
    kotlin("jvm") version "1.6.20"
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

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.2")
        implementation("com.fasterxml.jackson.core:jackson-core:2.13.2")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.13.2")


        implementation("org.slf4j:slf4j-api:1.7.32")
        implementation("ch.qos.logback:logback-classic:1.2.6")
        implementation("net.logstash.logback:logstash-logback-encoder:6.6")


        api("io.ktor:ktor-metrics-micrometer:$ktorVersion")
        api("io.micrometer:micrometer-registry-prometheus:1.7.4")

        testImplementation("no.nav:kafka-embedded-env:2.8.0")
        testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
        testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
        testImplementation("org.awaitility:awaitility:4.1.0")
    }
}