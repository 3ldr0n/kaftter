import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kafkaClientVersion = "2.3.0"
val slf4jVersion = "1.7.2"
val kotlinLoggingVersion = "1.6.24"
val hbcVersion = "2.2.0"

plugins {
    kotlin("jvm") version "1.3.41"
}

group = "kafka.twitter"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.kafka:kafka-clients:$kafkaClientVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("com.twitter:hbc-core:$hbcVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}