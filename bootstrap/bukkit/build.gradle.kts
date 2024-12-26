plugins {
    id("conventions.shadow")
    alias(libs.plugins.pluginyml.bukkit)
}

dependencies {
    implementation(projects.core)

    runtimeOnly(libs.slf4j.jdk14)
    compileOnly(libs.paper.api)
    implementation(libs.adventure.bukkit)
    implementation(libs.cloud.paper)
    implementation(libs.slf4j)
}

relocate("net.kyori")
relocate("org.spongepowered")

tasks.shadowJar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }
}

bukkit {
    name = rootProject.name
    main = "dev.triassic.template.bukkit.BukkitTemplatePlugin"
    apiVersion = "1.13"
    foliaSupported = true
}