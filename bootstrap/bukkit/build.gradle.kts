plugins {
    id("template.java-conventions")
}

dependencies {
    compileOnly(libs.paper.api)
    implementation(project(":common"))
}