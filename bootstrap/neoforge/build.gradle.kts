plugins {
    id("conventions.base")
    id("net.neoforged.moddev")
    id("com.gradleup.shadow")
}

val shade by configurations.creating

configurations {
    implementation {
        extendsFrom(shade)
    }
}

neoForge {
    version = libs.versions.neoforge.get()
    validateAccessTransformers = true
}

dependencies {
    implementation(libs.adventure.api)
    jarJar(libs.adventure.api)
    implementation(libs.adventure.minimessage)
    jarJar(libs.adventure.minimessage)
    jarJar(libs.adventure.neoforge)
    implementation(libs.adventure.neoforge)
    implementation(libs.cloud.neoforge)
    jarJar(libs.cloud.neoforge)
    shade(project(mapOf("path" to ":core", "configuration" to "shadow")))
}

tasks {
    shadowJar {
        configurations = listOf(shade)
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("shaded")
    }
}

val productionJar = tasks.register<Zip>("productionJar") {
    archiveClassifier = "final"
    archiveExtension = "jar"
    destinationDirectory = layout.buildDirectory.dir("libs")
    from(tasks.jarJar)
    from(zipTree(tasks.shadowJar.flatMap { it.archiveFile }))
}

tasks.assemble {
    dependsOn(productionJar)
}

tasks.named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    exclude("org/incendo/cloud/**")
    exclude("META-INF/services/org.incendo.cloud.*")
    exclude("org/slf4j/**")
    relocate("io.leangen.geantyref", "dev.triassic.template.lib.geantyref")
}