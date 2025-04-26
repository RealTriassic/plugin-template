plugins {
    id("conventions.shadow")
}

dependencies {
    implementation(projects.core)

    runtimeOnly(libs.slf4j.jdk14)
    compileOnly(libs.spigot.api)
    implementation(libs.adventure.bukkit)
    implementation(libs.cloud.paper)
    implementation(libs.slf4j)
}

relocate("net.kyori")
relocate("org.spongepowered")