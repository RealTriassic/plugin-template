plugins {
    id("io.freefair.lombok")
    id("template.shadow-conventions")
}

tasks {
    processResources {
        filesMatching(listOf("plugin.yml", "bungee.yml", "extension.yml")) {
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