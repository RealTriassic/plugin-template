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
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", project.version.toString())
                property("gitBranch", indraGit.branchName())
                property("gitCommit", indraGit.commit()?.name)
            }
        }
    }
}