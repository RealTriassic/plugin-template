plugins {
    id("architectury-plugin")
    id("dev.architectury.loom") version "1.11-SNAPSHOT"
}

architectury {
    minecraft = "1.21.8"
    common("fabric", "neoforge")
}

loom {
    silentMojangMappingsLicense()
}

dependencies {
    minecraft("net.minecraft:minecraft:1.21.8")
    mappings(loom.officialMojangMappings())
}