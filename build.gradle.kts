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

    org.lwjgl.Lwjgl { implementation(glfw, opengl) }

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("commons-cli:commons-cli:1.5.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}