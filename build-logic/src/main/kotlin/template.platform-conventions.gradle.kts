plugins {
    `java-library`
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    processResources {
        filesMatching(listOf("plugin.yml", "bungee.yml")) {
            expand(
                "id" to "plugin-template",
                "name" to "TemplatePlugin",
                "version" to project.version,
                "description" to project.description,
                "url" to "https://triassic.dev",
                "author" to "Triassic"
            )
        }
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))