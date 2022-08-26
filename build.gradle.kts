plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.mcbanners"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.triumphteam.dev/snapshots")
}

dependencies {
    implementation("dev.triumphteam:triumph-cmd-jda-slash:2.0.0-SNAPSHOT")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.13.3")
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "com.mcbanners.discordbot.MCBannersBot"
        }
    }

    shadowJar {
        archiveFileName.set("discord-bot.jar")
    }
}
