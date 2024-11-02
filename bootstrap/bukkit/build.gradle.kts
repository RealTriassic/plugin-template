plugins {
    id("template.platform-conventions")
}

dependencies {
    compileOnly(libs.paper.api)
    implementation(project(":common"))
}