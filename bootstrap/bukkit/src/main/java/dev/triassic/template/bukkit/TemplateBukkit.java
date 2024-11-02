package dev.triassic.template.bukkit;

import dev.triassic.template.common.TemplateBootstrap;
import dev.triassic.template.common.TemplateImpl;
import dev.triassic.template.common.util.PlatformType;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.nio.file.Path;

public class TemplateBukkit extends JavaPlugin implements TemplateBootstrap {

    @Override
    public void onEnable() {
        new TemplateImpl(PlatformType.BUKKIT, this);
    }

    public Path dataFolder() {
        return this.getDataFolder().toPath();
    }

    public Logger logger() {
        return this.getSLF4JLogger();
    }
}
