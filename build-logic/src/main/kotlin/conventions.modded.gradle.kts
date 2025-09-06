plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("com.gradleup.shadow")
}

architectury {
    minecraft = "1.20.6"
}

loom {
    silentMojangMappingsLicense()
}

tasks {
    shadowJar {
        configurations = listOf(project.configurations.shadow.get())
        archiveVersion.set(project.version.toString())
        archiveClassifier.set("shaded")
    }

    remapJar {
        dependsOn(shadowJar)
        inputFile.set(shadowJar.get().archiveFile)
        archiveClassifier.set("")
        archiveVersion.set("")
    }
}

dependencies {
    minecraft("net.minecraft:minecraft:1.20.6")
    mappings(loom.officialMojangMappings())
}