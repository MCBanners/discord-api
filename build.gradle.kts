plugins {
    id("org.springframework.boot") version "3.0.4"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    id("java")
}

group = "com.mcbanners"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
    maven("https://repo.triumphteam.dev/snapshots")
}

dependencies {
    implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2021.0.4"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("com.github.ben-manes.caffeine:caffeine:3.1.1")
    implementation("dev.triumphteam:triumph-cmd-jda-slash:2.0.0-SNAPSHOT")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.4")
    implementation("com.google.guava:guava:31.1-jre")

    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks {
    bootJar {
        archiveFileName.set("discordapi.jar")
    }
}
