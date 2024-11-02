repositories {
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
}

dependencies {
    compileOnly(libs.bungeecord.api)
    implementation(project(":common"))
}