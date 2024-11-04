plugins {
    id("template.platform-conventions")
    alias(libs.plugins.shadow)
}

dependencies {
    compileOnly(libs.paper.api)
    implementation(project(":core"))
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set(null as String?)

        listOf(
            "org.spongepowered"
        ).forEach {
            relocate(it, "dev.triassic.template.lib.$it")
        }

        doLast {
            copy {
                from(archiveFile)
                into("${rootProject.projectDir}/build")
            }
        }
    }
}