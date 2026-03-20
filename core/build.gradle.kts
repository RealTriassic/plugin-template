plugins {
    id("conventions.shadow")
    alias(libs.plugins.blossom)
    alias(libs.plugins.indra.git)
}

dependencies {
    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimessage)
    implementation(libs.cloud.core)
    implementation(libs.cloud.minecraft.extras)
    implementation(libs.configurate.yaml)
    implementation(libs.slf4j)
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("name", rootProject.name)
                property("description", project.description.toString())
                property("version", project.version.toString())
                property("url", project.property("url").toString())
                property("author", project.property("author").toString())
                property("gitBranch", indraGit.branchName())
                property("gitCommit", indraGit.commit().get().name)
            }
        }
    }
}