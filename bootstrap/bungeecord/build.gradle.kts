plugins {
    id("template.platform-conventions")
}

dependencies {
    compileOnly(libs.bungeecord.api)
    implementation(project(":core"))
}