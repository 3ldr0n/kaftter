import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

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
    implementation("org.apache.kafka:kafka-clients:2.3.0")
    implementation("org.slf4j:slf4j-api:1.7.2")
    implementation("io.github.microutils:kotlin-logging:1.6.24")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}