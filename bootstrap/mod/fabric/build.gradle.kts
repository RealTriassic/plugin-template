plugins {
    id("conventions.modded")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:0.16.10")
    modApi("net.fabricmc.fabric-api:fabric-api:0.119.5+1.21.5")
}