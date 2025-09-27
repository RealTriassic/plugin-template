enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        mavenCentral()
        maven("https://repo.opencollab.dev/main/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/") {
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}

pluginManagement {
    includeBuild("build-logic")

    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.architectury.dev/")
        maven("https://files.minecraftforge.net/maven/")
        maven("https://repo.papermc.io/repository/maven-snapshots/") {
            mavenContent {
                snapshotsOnly()
            }
        }
    }
}

plugins {
    id("dev.architectury.loom") version "1.11-SNAPSHOT"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "plugin-template"

include(":core")

file("bootstrap").listFiles()?.forEach { file ->
    if (file.isDirectory) {
        include(":bootstrap:${file.name}")

        if (file.name == "mod") {
            listOf("fabric", "neoforge").forEach { submodule ->
                include(":bootstrap:mod:$submodule")
            }
        }
    }
}