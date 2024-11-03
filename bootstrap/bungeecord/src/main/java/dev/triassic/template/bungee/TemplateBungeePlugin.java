package dev.triassic.template.bungee;

import dev.triassic.template.common.TemplateBootstrap;
import dev.triassic.template.common.TemplateImpl;
import dev.triassic.template.common.TemplateLogger;
import dev.triassic.template.common.util.PlatformType;
import net.md_5.bungee.api.plugin.Plugin;

import java.nio.file.Path;

public class TemplateBungeePlugin extends Plugin implements TemplateBootstrap {

    private final TemplateBungeeLogger logger = new TemplateBungeeLogger(getLogger());

    @Override
    public void onEnable() {
        new TemplateImpl(PlatformType.BUNGEECORD, this);
    }

    public Path dataFolder() {
        return this.getDataFolder().toPath();
    }

    public TemplateLogger logger() {
        return this.logger;
    }
}
