plugins {
    kotlin("jvm") version "2.0.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    maven ("https://maven.aliyun.com/repository/central")
    maven ("https://maven.aliyun.com/repository/public")
    maven ("https://maven.aliyun.com/repository/google")
    maven ("https://maven.aliyun.com/repository/gradle-plugin")
    mavenCentral()
    gradlePluginPortal()
    maven ("https://jitpack.io")
    maven ("https://oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    testImplementation(kotlin("test"))

    implementation("io.ktor:ktor-server-core:2.3.1")
    implementation("io.ktor:ktor-server-netty:2.3.1")
    implementation("io.ktor:ktor-server-websockets:2.3.1")
    implementation("ch.qos.logback:logback-classic:1.2.11")

    implementation("org.java-websocket:Java-WebSocket:1.5.7")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(11)
}