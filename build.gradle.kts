plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.github.ben-manes.versions") version "0.51.0"
    id("java")
}

group = "com.mcbanners"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
    maven("https://repo.triumphteam.dev/snapshots")
}

dependencies {
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2023.0.2"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
    implementation("org.incendo:cloud-jda5:1.0.0-beta.3")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.1")
    implementation("com.google.guava:guava:33.2.1-jre")

    implementation("net.dv8tion:JDA:5.0.0") {
        exclude(module = "opus-java")
    }

    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks {
    bootJar {
        archiveFileName.set("discordapi.jar")
    }
}
