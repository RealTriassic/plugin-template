plugins {
    id("conventions.shadow")
    alias(libs.plugins.pluginyml.bungeecord)
}

dependencies {
    implementation(project(":core"))

    compileOnly(libs.bungeecord.api)
    implementation(libs.adventure.bungeecord)
    implementation(libs.cloud.bungeecord)
}

bungee {
    name = rootProject.name
    main = "dev.triassic.template.bungee.TemplateBungee"
}