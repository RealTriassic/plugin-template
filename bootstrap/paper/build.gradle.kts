plugins {
    id("conventions.shadow")
}

dependencies {
    api(projects.core)

    compileOnly(libs.paper.api)
    implementation(libs.cloud.paper)
}

relocate("org.spongepowered")