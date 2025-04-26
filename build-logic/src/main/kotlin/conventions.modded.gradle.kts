plugins {
    id("conventions.shadow")
    id("dev.architectury.loom")
    id("architectury-plugin")
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