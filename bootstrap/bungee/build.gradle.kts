plugins {
    id("template.java-conventions")
}

dependencies {
    compileOnly(libs.bungeecord.api)
    implementation(project(":common"))
}