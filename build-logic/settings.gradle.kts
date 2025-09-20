@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://maven.architectury.dev/")
        maven("https://maven.fabricmc.net/")
        maven("https://files.minecraftforge.net/maven/")
    }

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"