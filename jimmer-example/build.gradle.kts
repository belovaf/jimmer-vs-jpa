import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.spring") version "2.0.20"
    id("com.google.devtools.ksp") version "2.0.20-1.0.25"
}

val jimmerVersion = "0.8.184"

java {
    sourceCompatibility = providers.gradleProperty("java.version").map(JavaVersion::toVersion).get()
}

tasks.withType(KotlinJvmCompile::class).configureEach {
    compilerOptions {
        jvmTarget.set(provider { JvmTarget.fromTarget(java.targetCompatibility.toString()) })
    }
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(project(":schema"))
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.3.4"))
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-jdbc")
    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:$jimmerVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.6.0")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

    ksp("org.babyfish.jimmer:jimmer-ksp:$jimmerVersion")

    implementation("io.leangen.graphql:graphql-spqr-spring-boot-starter:1.0.1")
    implementation("com.graphql-java:graphql-java:21.5!!")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.testcontainers:postgresql")
}