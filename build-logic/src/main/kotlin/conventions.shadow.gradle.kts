plugins {
    id("conventions.base")
    id("com.gradleup.shadow")
}

interface Injected {
    @get:Inject val fs: FileSystemOperations
}

tasks {
    val shadowJar by existing(com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar::class) {
        archiveBaseName.set("${rootProject.name}-${project.name}")
        archiveClassifier.set(null as String?)
        mergeServiceFiles()
    }

    register<Copy>("copyShadowJar") {
        dependsOn(shadowJar)
        from(shadowJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory)
    }

    named("build") {
        dependsOn("copyShadowJar")
    }
}