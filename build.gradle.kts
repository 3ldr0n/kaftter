import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kafkaClientVersion = "2.3.0"
val slf4jVersion = "1.7.2"
val kotlinLoggingVersion = "1.6.24"
val hbcVersion = "2.2.0"

plugins {
    kotlin("jvm") version "1.3.41"
}

group = "kaftter"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.kafka:kafka-clients:$kafkaClientVersion")
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
    implementation("io.github.microutils:kotlin-logging:$kotlinLoggingVersion")
    implementation("com.twitter:hbc-core:$hbcVersion")
    implementation("org.elasticsearch.client:elasticsearch-rest-high-level-client:6.4.0")
    implementation("com.google.code.gson:gson:2.8.5")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}