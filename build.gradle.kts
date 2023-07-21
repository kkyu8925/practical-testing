import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"

    id("org.asciidoctor.jvm.convert") version "3.3.2"
}

group = "sample"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    // spring
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")

    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // h2
    runtimeOnly("com.h2database:h2")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.ninja-squad:springmockk:4.0.2")
    // spring rest docs
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")

    // Guava
    implementation("com.google.guava:guava:32.1.1-jre")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val docDir = "src/main/resources/static/docs"

tasks.asciidoctor {
    forkOptions {
        jvmArgs("--add-opens", "java.base/sun.nio.ch=ALL-UNNAMED")
        jvmArgs("--add-opens", "java.base/java.io=ALL-UNNAMED")
    }

    dependsOn(tasks.test) // test snippets 파일이 생성되므로 test 이후에 실행
    baseDirFollowsSourceDir() // 기본 경로 설정(/src/docs/asciidoc), include 파일의 경로
    doFirst {
        delete(docDir)
    }
    doLast { // 생성된 파일 복사
        copy {
            from(outputDir)
            into(docDir)
        }
    }
}

tasks.build {
    dependsOn(tasks.asciidoctor)
}

tasks.bootJar {
    dependsOn(tasks.asciidoctor)
}
