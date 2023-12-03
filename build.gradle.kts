

plugins {
    kotlin("jvm") version "1.9.0"
    application
}

group = "ie.setu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // dependencies for logging
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    //For Streaming to XML and JSON
    implementation("com.thoughtworks.xstream:xstream:1.4.20")
    implementation("org.codehaus.jettison:jettison:1.5.4")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}