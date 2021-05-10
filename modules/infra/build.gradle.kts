plugins {
    kotlin("jvm")
    kotlin("plugin.allopen")
    id("io.quarkus")
}



repositories {
    mavenLocal()
    mavenCentral()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    api("io.quarkus:quarkus-arc")
    api("io.quarkus:quarkus-opentelemetry")
    api("io.quarkus:quarkus-opentelemetry-exporter-jaeger")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3")
    api("io.smallrye.reactive:mutiny-kotlin:0.14.0")
    api(project(path=":core"))


}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}
