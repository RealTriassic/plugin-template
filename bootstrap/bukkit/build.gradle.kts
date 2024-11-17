plugins {
    id("conventions.shadow")
}

dependencies {
    compileOnly(libs.paper.api)
    implementation(libs.cloud.paper)

    implementation(project(":core"))
}