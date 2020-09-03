import java.time.LocalDateTime

val mainClass = "br.com.devlhse.kotlinauthservice.application.ApplicationMain"

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.postgresql:postgresql:42.2.11")
    }
}

plugins {
    kotlin("jvm") version "1.3.61"
    id("org.flywaydb.flyway") version "6.3.2"
    id("io.gitlab.arturbosch.detekt") version "1.7.2"
    id("jacoco")
}

group = "br.com.devlhse.kotlinauthservice"
version = "1.0.0-SNAPSHOT"

jacoco {
    toolVersion = "0.8.5"
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("io.javalin:javalin:3.9.0")
    implementation("org.slf4j:slf4j-simple:1.7.28")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.10.1")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.1")
    implementation("org.koin:koin-core:2.1.5")
    implementation("com.zaxxer:HikariCP:3.2.0")
    implementation("org.postgresql:postgresql:42.2.11")
    implementation("com.natpryce:konfig:1.6.10.0")
    implementation("com.auth0:java-jwt:3.10.3")
    implementation("org.jetbrains.exposed:exposed-core:0.25.1")
    implementation("org.jetbrains.exposed:exposed-java-time:0.25.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.25.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.25.1")
    implementation("org.apache.logging.log4j:log4j-api:2.13.1")
    implementation("org.apache.logging.log4j:log4j-core:2.13.1")
    implementation("com.jcabi:jcabi-manifests:1.1")
    implementation("com.github.kittinunf.fuel:fuel:2.2.3")
    implementation("com.github.kittinunf.fuel:fuel-jackson:2.2.3")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.koin:koin-test:2.1.5")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.1")
    testImplementation("com.github.tomakehurst:wiremock:2.27.1")
    testImplementation("io.rest-assured:rest-assured:4.3.0")
    testImplementation("com.opentable.components:otj-pg-embedded:0.13.1")
    testImplementation("org.flywaydb:flyway-core:6.4.1")
}

tasks.jacocoTestReport {
    dependsOn(":test")
    reports {
        xml.isEnabled = true
        html.isEnabled = true
    }
}

tasks.jacocoTestCoverageVerification {
    dependsOn(":test")
    violationRules {
        rule {
            limit {
                minimum = "0.59".toBigDecimal()
            }
        }
    }

    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude("br/com/devlhse/kotlinauthservice/application/**")
            exclude("br/com/devlhse/kotlinauthservice/config/**")
            exclude("br/com/devlhse/kotlinauthservice/domain/model/**")
            exclude("br/com/devlhse/kotlinauthservice/domain/common/**")
            exclude("br/com/devlhse/kotlinauthservice/domain/repositories/**")
            exclude("br/com/devlhse/kotlinauthservice/exception/**")
            exclude("br/com/devlhse/kotlinauthservice/resources/**")
        }
    )
}

tasks.jar {
    manifest {
        attributes("Main-Class" to mainClass)
        attributes("Package-Version" to archiveVersion)
        attributes("Build-Date" to LocalDateTime.now())
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    from(sourceSets.main.get().output)
}

tasks.withType<Test> { loadEnv(this, file("test.env")) }

tasks.test {
    useJUnitPlatform()
    finalizedBy(":jacocoTestReport", ":jacocoTestCoverageVerification")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

fun loadEnv(testTask: Test, file: File) {
    if (!file.exists()) throw IllegalArgumentException("failed to load envs from file, ${file.name}")

    file.readLines().forEach { line ->
        if (line.isBlank() || line.startsWith("#")) return@forEach
        line.split("=", limit = 2)
            .takeIf { it.size == 2 && !it[0].isBlank() }
            ?.run { Pair(this[0].trim(), this[1].trim()) }
            ?.run {
                testTask.environment(this.first, this.second)
            }
    }
}

val jacocoTestCoverageVerification = tasks.findByName("jacocoTestCoverageVerification")
tasks.check { dependsOn(jacocoTestCoverageVerification) }



