plugins {
    id("conventions.shadow")
    alias(libs.plugins.pluginyml.bungeecord)
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

bungee {
    name = rootProject.name
    main = "dev.triassic.template.bungee.BungeeTemplatePlugin"
}