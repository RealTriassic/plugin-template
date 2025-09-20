plugins {
    id("dev.architectury.loom") version "1.11-SNAPSHOT"
    id("architectury-plugin")
    id("conventions.modded")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

dependencies {
    minecraft("net.minecraft:minecraft:1.21.8")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.17.2")
    modImplementation(libs.adventure.api)
    include(libs.adventure.api)
    modImplementation(libs.adventure.minimessage)
    include(libs.adventure.minimessage)
    include(libs.adventure.fabric)
    modImplementation(libs.adventure.fabric)
    include(libs.cloud.fabric)
    modImplementation(libs.cloud.fabric)
    shade(project(mapOf("path" to ":core", "configuration" to "shadow")))
}

relocate("org.spongepowered")

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    exclude("org/incendo/cloud/**")
    exclude("META-INF/services/org.incendo.cloud.*")
    exclude("org/slf4j/**")
    relocate("io.leangen.geantyref", "dev.triassic.template.lib.geantyref")
}