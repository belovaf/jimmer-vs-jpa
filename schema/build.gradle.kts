plugins {
    java
}

java {
    sourceCompatibility = providers.gradleProperty("java.version").map(JavaVersion::toVersion).get()
}