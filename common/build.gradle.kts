plugins {
    alias(libs.plugins.shadow)
}

dependencies {
    compileOnly(libs.slf4j)
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimessage)
    implementation(libs.cloud.core)
    implementation(libs.cloud.minecraft.extras)
    implementation(libs.configurate.yaml)
}

tasks {
    build {
        dependsOn(shadowJar)
    }

    shadowJar {
        archiveBaseName.set(rootProject.name)
        archiveClassifier.set(null as String?)

        listOf(
            "org.spongepowered",
            "io.papermc.papertrail"
        ).forEach {
            relocate(it, "org.stupidcraft.infohud.lib.$it")
        }

        doLast {
            copy {
                from(archiveFile)
                into("${rootProject.projectDir}/build")
            }
        }
    }
}