/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Kotlin library project to get you started.
 * For more details take a look at the 'Building Java & JVM projects' chapter in the Gradle
 * User Manual available at https://docs.gradle.org/7.4/userguide/building_java_projects.html
 */
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.7.3"
    id("io.spring.dependency-management") version "1.0.13.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    signing
    `maven-publish`
}

group = "io.spektacle.spring.data.jdbc"
version = "1.0.5"

publishing {
    publications {
        create<MavenPublication>("spring-data-jdbc-irisdialect") {
            artifactId = "irisdialect"
            from(components["kotlin"])
        }
    }

    repositories {
        maven {
            name = "github"
            url = uri("https://maven.pkg.github.com/jjones028/pkgs")
            credentials {
                username = project.findProperty("pkgsUsername") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("pkgsPassword") as String? ?: System.getenv("TOKEN")
            }
        }
    }
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}
