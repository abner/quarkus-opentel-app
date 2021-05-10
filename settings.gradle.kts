pluginManagement {
    val quarkusPluginVersion: String by settings
    val quarkusPluginId: String by settings
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
    }
}
rootProject.name="quarkus-opentel-app"

include(
    /** Módulos com classes compartilhadas entre os projetos **/
    "core",
    "infra",
    "docs"
)


project(":core").projectDir = file("modules/core")
project(":infra").projectDir = file("modules/infra")
project(":docs").projectDir = file("modules/docs")
