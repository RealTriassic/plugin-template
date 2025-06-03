plugins {
    id("conventions.modded")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

dependencies {
    modImplementation("net.fabricmc:fabric-loader:0.16.14")
}