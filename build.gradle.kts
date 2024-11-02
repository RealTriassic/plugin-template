plugins {
    java
    id("io.freefair.lombok") version "8.10.2" apply false
}

allprojects {
    apply<JavaPlugin>()
    apply(plugin = "io.freefair.lombok")

    dependencies {
        compileOnly("org.projectlombok", "lombok", "1.18.34")
        annotationProcessor("org.projectlombok", "lombok", "1.18.34")

        testCompileOnly("org.projectlombok", "lombok", "1.18.34")
        testAnnotationProcessor("org.projectlombok", "lombok", "1.18.34")
    }

    tasks {
        compileJava {
            options.encoding = Charsets.UTF_8.name()
            options.release.set(17)
        }
    }

    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}