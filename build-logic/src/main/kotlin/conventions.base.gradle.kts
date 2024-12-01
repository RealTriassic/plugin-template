import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    `java-library`
    checkstyle
    id("io.freefair.lombok")
    id("com.diffplug.spotless")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
    }
}

configure<CheckstyleExtension> {
    maxWarnings = 0
    toolVersion = libs.checkstyle.get().version.toString()
    configProperties = mapOf(
        "org.checkstyle.google.suppressionfilter.config" to configDirectory.file("suppressions.xml").get().toString()
    )
}

configure<SpotlessExtension> {
    java {
        removeUnusedImports()
        targetExclude("build/generated/**") // Exclude all generated files in build directory
        licenseHeaderFile(rootProject.file("config/spotless/LICENSE_HEADER.txt"))
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
