plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    // Used by LibsAccessor.kt, well known workaround for accessing
    // library versions from the version catalog in build logic.
    // See https://github.com/gradle/gradle/issues/15383#issuecomment-779893192
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    implementation("io.freefair.gradle:lombok-plugin:8.10.2")
}