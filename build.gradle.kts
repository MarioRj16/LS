import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "org.postgresql", name = "postgresql", version = "42.+")
    implementation(group = "org.http4k", name = "http4k-core", version = "4.40.+")
    implementation(group = "org.http4k", name = "http4k-server-jetty", version = "4.40.+")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-serialization-json", version = "1.5.+")
    implementation(group = "org.jetbrains.kotlinx", name = "kotlinx-datetime", version = "0.4.+")
    implementation(group = "org.slf4j", name = "slf4j-api", version = "2.0.0-alpha5")
    implementation("org.mindrot:jbcrypt:0.4")
    runtimeOnly(group = "org.slf4j", name = "slf4j-simple", version = "2.0.0-alpha5")
    testImplementation(kotlin("test"))
}

application {
    mainClassName = "pt.isel.ls.ServerKt"
}

ktlint {
    ignoreFailures.set(true)
}

tasks.test {
    useJUnitPlatform()
    exclude("**/*Postgres*")
}

tasks.register<Test>("testAll") {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

tasks.register<Copy>("copyRuntimeDependencies") {
    into("build/libs")
    from(configurations.runtimeClasspath)
}


tasks.named<Jar>("jar") {
    dependsOn("copyRuntimeDependencies")
    manifest{
        attributes["Main-Class"] = "pt.isel.ls.ServerKt"
        attributes["Class-Path"] = configurations.runtimeClasspath.get().joinToString(" ") { it.name }
    }
}
