plugins {
    id("template.platform-conventions")
}

dependencies {
    compileOnly(libs.geyser.api)
    implementation(project(":common"))
}