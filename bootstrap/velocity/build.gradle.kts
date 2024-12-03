plugins {
    id("conventions.shadow")
}

dependencies {
    implementation(projects.core)

    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
    implementation(libs.cloud.velocity)
}