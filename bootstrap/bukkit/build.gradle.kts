plugins {
    id("conventions.shadow")
    alias(libs.plugins.pluginyml.bukkit)
}

dependencies {
    implementation(project(":core"))

    compileOnly(libs.paper.api)
    implementation(libs.adventure.bukkit)
    implementation(libs.cloud.paper)
}

tasks.shadowJar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }
}

bukkit {
    name = rootProject.name
    main = "dev.triassic.template.bukkit.TemplateBukkit"
    apiVersion = "1.13"
    foliaSupported = true
}