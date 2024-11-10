plugins {
    id("conventions.shadow")
}

dependencies {
    implementation(project(":core"))

    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
}