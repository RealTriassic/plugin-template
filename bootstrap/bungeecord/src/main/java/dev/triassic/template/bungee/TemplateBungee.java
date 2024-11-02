package dev.triassic.template.bungee;

import net.md_5.bungee.api.plugin.Plugin;

import java.nio.file.Path;

public class TemplateBungee extends Plugin {

    @Override
    public void onEnable() {
        // bungeecord doesn't have an slf4j implementation i think, need to think of something
    }

    public Path dataFolder() {
        return this.getDataFolder().toPath();
    }
}
