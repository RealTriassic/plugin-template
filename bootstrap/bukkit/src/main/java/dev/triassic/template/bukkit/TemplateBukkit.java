package dev.triassic.template.bukkit;

import dev.triassic.template.TemplateBootstrap;
import dev.triassic.template.TemplateImpl;
import dev.triassic.template.util.TemplateLogger;
import dev.triassic.template.util.PlatformType;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public final class TemplateBukkit extends JavaPlugin implements TemplateBootstrap {

    private final TemplateBukkitLogger logger = new TemplateBukkitLogger(getLogger());

    @Override
    public void onEnable() {
        new TemplateImpl(PlatformType.BUKKIT, this);
    }

    public Path dataDirectory() {
        return this.getDataFolder().toPath();
    }

    public TemplateLogger templateLogger() {
        return this.logger;
    }
}
