plugins {
    id("conventions.shadow")
}

dependencies {
    compileOnly(libs.bungeecord.api)
    implementation(libs.adventure.bungeecord)
    implementation(libs.cloud.bungeecord)

    implementation(project(":core"))
}