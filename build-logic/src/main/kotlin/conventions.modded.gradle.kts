plugins {
    id("conventions.base")
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("com.gradleup.shadow")
}

architectury {
    minecraft = "1.21.8"
}

loom {
    silentMojangMappingsLicense()
}

configurations {
    create("shade")
    implementation {
        extendsFrom(configurations.getByName("shade"))
    }
}


tasks {
    shadowJar {
        configurations = listOf(
            project.configurations["shade"],
        )
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

dependencies {
    minecraft("net.minecraft:minecraft:1.21.8")
    mappings(loom.officialMojangMappings())
}