plugins {
    id("conventions.base")
    id("com.gradleup.shadow")
}

tasks {
    shadowJar {
        archiveBaseName.set("${rootProject.name}-${project.name}")
        archiveClassifier.set(null as String?)

        val groupName = project.properties["group"] as String

        listOf(
            "net.kyori",
            "org.spongepowered"
        ).forEach { packageToRelocate ->
            relocate(packageToRelocate, "$groupName.lib.$packageToRelocate")
        }

        doLast {
            copy {
                from(archiveFile)
                into("${rootProject.projectDir}/build")
            }
        }
    }

    named("build") {
        dependsOn(shadowJar)
    }
}