plugins {
    `kotlin-dsl`
}

dependencies {
    // Used by LibsAccessor.kt, well known workaround for accessing
    // library versions from the version catalog in build logic.
    // See https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation(libs.architectury.loom)
    implementation(libs.architectury.plugin)
    implementation(libs.lombok.plugin)
    implementation(libs.shadow.plugin)
    implementation(libs.spotless.plugin)
}