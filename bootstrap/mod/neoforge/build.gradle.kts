plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

repositories {
    maven("https://maven.neoforged.net/releases")
}

dependencies {
    neoForge("net.neoforged:neoforge:20.6.134")
    implementation(projects.bootstrap.mod)
    implementation(projects.core)
}