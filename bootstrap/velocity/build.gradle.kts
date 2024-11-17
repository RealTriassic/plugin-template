plugins {
    id("conventions.shadow")
}

dependencies {
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
    implementation(libs.cloud.velocity)

    implementation(project(":core"))
}