plugins {
    id("conventions.modded")
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
}