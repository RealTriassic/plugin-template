package dev.triassic.template.bukkit;

import dev.triassic.template.common.TemplateBootstrap;
import dev.triassic.template.common.TemplateImpl;
import dev.triassic.template.common.TemplateLogger;
import dev.triassic.template.common.util.PlatformType;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Path;

public class TemplateBukkitPlugin extends JavaPlugin implements TemplateBootstrap {

    private final TemplateBukkitLogger logger = new TemplateBukkitLogger(getLogger());

    @Override
    public void onEnable() {
        new TemplateImpl(PlatformType.BUKKIT, this);
    }

    public Path dataFolder() {
        return this.getDataFolder().toPath();
    }

    public TemplateLogger logger() {
        return this.logger;
    }
}
