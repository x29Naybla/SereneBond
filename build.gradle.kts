import org.lwjgl.Lwjgl.Module.*;

plugins {
    id("java")
    id("org.lwjgl.plugin") version "0.0.20"
}

group = "com.serenebond"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    org.lwjgl.Lwjgl { implementation(glfw, opengl, stb) }

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("commons-cli:commons-cli:1.5.0")

    implementation("org.joml:joml:1.10.5")

    implementation("it.unimi.dsi:fastutil:8.5.11")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}