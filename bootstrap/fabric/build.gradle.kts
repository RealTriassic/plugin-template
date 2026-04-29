plugins {
    id("conventions.base")
    id("net.fabricmc.fabric-loom")
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
    implementation(libs.fabric.loader)
    implementation(libs.fabric.api)
    implementation(libs.fabric.permissions.api)
    include(libs.fabric.permissions.api)
    implementation(libs.adventure.api)
    include(libs.adventure.api)
    implementation(libs.adventure.minimessage)
    include(libs.adventure.minimessage)
    include(libs.adventure.fabric)
    implementation(libs.adventure.fabric)
    include(libs.cloud.fabric)
    implementation(libs.cloud.fabric)
    shade(project(mapOf("path" to ":core", "configuration" to "shadow")))
}

tasks {
    shadowJar {
        configurations = listOf(shade)
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("shaded")
        mergeServiceFiles()
    }

    jar {
        dependsOn(shadowJar)
        // inputFile.set(shadowJar.get().archiveFile)
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
