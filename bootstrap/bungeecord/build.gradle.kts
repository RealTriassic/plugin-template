plugins {
    id("conventions.shadow")
}

dependencies {
    implementation(projects.core)

    runtimeOnly(libs.slf4j.jdk14)
    compileOnly(libs.bungeecord.api)
    implementation(libs.adventure.bungeecord)
    implementation(libs.cloud.bungeecord)
    implementation(libs.slf4j)
}

relocate("net.kyori")
relocate("org.spongepowered")