rootProject.name = "jimmer-vs-jpa"
include(
    "jimmer-example",
    "jpa-example",
    "schema"
)

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}