plugins {
    id("conventions.base")
    alias(libs.plugins.fabric.loom)
    id("com.gradleup.shadow")
}

val shade by configurations.creating

configurations {
    implementation {
        extendsFrom(shade)
    }
}

dependencies {
    minecraft(libs.minecraft)
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:0.17.3")
    modImplementation("net.fabricmc.fabric-api:fabric-api:0.138.0+1.21.10")
    modImplementation("me.lucko:fabric-permissions-api:0.5.0")
    include("me.lucko:fabric-permissions-api:0.5.0")
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

tasks {
    shadowJar {
        configurations = listOf(shade)
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("shaded")
        mergeServiceFiles()
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile.set(shadowJar.get().archiveFile)
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    exclude("org/incendo/cloud/**")
    exclude("META-INF/services/org.incendo.cloud.*")
    exclude("org/slf4j/**")
    relocate("io.leangen.geantyref", "dev.triassic.template.lib.geantyref")
}
