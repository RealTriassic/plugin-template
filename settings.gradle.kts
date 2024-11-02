@file:Suppress("UnstableApiUsage")

rootProject.name = "plugin-template"

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven("https://repo.opencollab.dev/main/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include(":common")

file("bootstrap").listFiles()?.forEach { file ->
    if (file.isDirectory) {
        include(":bootstrap:${file.name}")
    }
}