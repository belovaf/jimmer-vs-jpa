import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("jvm") version "2.0.20"
    kotlin("plugin.lombok") version "2.0.20"
    kotlin("plugin.jpa") version "2.0.20"
    kotlin("plugin.spring") version "2.0.20"
    id("io.freefair.lombok") version "8.10.2"
//    id("org.hibernate.orm") version "6.5.3.Final"
}

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

kotlinLombok {
    lombokConfigurationFile(file("lombok.config"))
}

dependencies {
    val springBootPlatform = platform("org.springframework.boot:spring-boot-dependencies:3.3.4")

    implementation(project(":schema"))
    implementation(springBootPlatform)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    annotationProcessor(springBootPlatform)
    annotationProcessor("org.hibernate.orm:hibernate-jpamodelgen")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.testcontainers:postgresql")
}

//hibernate {
//    enhancement {
//        enableLazyInitialization = true
//    }
//}