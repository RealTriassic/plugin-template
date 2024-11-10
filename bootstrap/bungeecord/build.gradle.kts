plugins {
    id("conventions.shadow")
}

dependencies {
    implementation(project(":core"))

    compileOnly(libs.bungeecord.api)
}