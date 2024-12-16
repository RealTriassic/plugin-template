plugins {
    id("conventions.base")
    id("com.gradleup.shadow")
}

tasks {
    shadowJar {
        archiveBaseName.set("${rootProject.name}-${project.name}")
        archiveClassifier.set(null as String?)

        doLast {
            copy {
                from(archiveFile)
                into("${rootProject.projectDir}/build")
            }
        }
    }

    named("build") {
        dependsOn(shadowJar)
    }
}