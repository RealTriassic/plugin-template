plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    id("com.gradleup.shadow")
}

architectury {
    minecraft = "1.20.6"
}

loom {
    silentMojangMappingsLicense()
}


dependencies {
    minecraft("net.minecraft:minecraft:1.20.6")
    mappings(loom.officialMojangMappings())
}