plugins {
    id("dev.architectury.loom") version "1.11-SNAPSHOT"
    id("architectury-plugin")
    id("conventions.modded")
}

architectury {
    platformSetupLoomIde()
    neoForge()
}

dependencies {
    modImplementation(libs.adventure.api)
    include(libs.adventure.api)
    modImplementation(libs.adventure.minimessage)
    include(libs.adventure.minimessage)
    minecraft("net.minecraft:minecraft:1.21.8")
    mappings(loom.officialMojangMappings())
    neoForge("net.neoforged:neoforge:21.8.46")
    include(libs.adventure.neoforge)
    modImplementation(libs.adventure.neoforge)
    include(libs.cloud.neoforge)
    modImplementation(libs.cloud.neoforge)
    shade(project(mapOf("path" to ":core", "configuration" to "shadow")))
}

relocate("org.spongepowered")

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    exclude("org/incendo/cloud/**")
    exclude("META-INF/services/org.incendo.cloud.*")
    exclude("org/slf4j/**")
    relocate("io.leangen.geantyref", "dev.triassic.template.lib.geantyref")
}