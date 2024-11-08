package dev.triassic.template.bungee;

import dev.triassic.template.TemplateBootstrap;
import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplateLogger;
import dev.triassic.template.util.PlatformType;
import net.md_5.bungee.api.plugin.Plugin;

import java.nio.file.Path;

public class TemplateBungee extends Plugin implements TemplateBootstrap {

    private final TemplateBungeeLogger logger = new TemplateBungeeLogger(getLogger());

    @Override
    public void onEnable() {
        new TemplateImpl(PlatformType.BUNGEECORD, this);
    }

    public Path dataDirectory() {
        return this.getDataFolder().toPath();
    }

    public TemplateLogger templateLogger() {
        return this.logger;
    }
}
