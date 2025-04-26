plugins {
    id("conventions.shadow")
}

dependencies {
    implementation(projects.core)

    compileOnly(libs.paper.api)
    implementation(libs.cloud.paper)
}

relocate("org.spongepowered")

tasks.shadowJar {
    manifest {
        attributes["paperweight-mappings-namespace"] = "mojang"
    }
}