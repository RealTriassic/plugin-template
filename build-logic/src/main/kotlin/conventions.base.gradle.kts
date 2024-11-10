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

    processResources {
        filesMatching(
            listOf(
                "plugin.yml", "bungee.yml", "extension.yml"
            )
        ) {
            expand(
                "id" to rootProject.name,
                "name" to "TemplatePlugin",
                "version" to project.version,
                "description" to project.description,
                "url" to "https://triassic.dev",
                "author" to "Triassic"
            )
        }
    }
}

configure<CheckstyleExtension> {
    maxErrors = 0
    maxWarnings = 0
    toolVersion = libs.checkstyle.get().version.toString()
    configFile = rootProject.file("config/checkstyle/checkstyle.xml")
}

configure<SpotlessExtension> {
    java {
        licenseHeaderFile(rootProject.file("config/spotless/LICENSE_HEADER.txt"))
        removeUnusedImports()
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
