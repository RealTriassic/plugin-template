plugins {
    id("conventions.shadow")
}

dependencies {
    compileOnly(libs.paper.api)
    implementation(libs.adventure.bukkit)
    implementation(libs.cloud.paper)

    implementation(project(":core"))
}