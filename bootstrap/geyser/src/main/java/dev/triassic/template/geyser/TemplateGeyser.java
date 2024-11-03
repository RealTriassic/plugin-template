package dev.triassic.template.geyser;

import dev.triassic.template.common.TemplateBootstrap;
import dev.triassic.template.common.TemplateImpl;
import dev.triassic.template.common.TemplateLogger;
import dev.triassic.template.common.util.PlatformType;
import org.geysermc.event.subscribe.Subscribe;
import org.geysermc.geyser.api.event.lifecycle.GeyserPostInitializeEvent;
import org.geysermc.geyser.api.extension.Extension;

import java.nio.file.Path;

public class TemplateGeyser implements Extension, TemplateBootstrap {

    private final TemplateGeyserLogger logger = new TemplateGeyserLogger(logger());

    @Subscribe
    public void onPostInitialize(GeyserPostInitializeEvent event) {
        new TemplateImpl(PlatformType.GEYSER, this);
    }

    public Path templateDataFolder() {
        return this.dataFolder();
    }

    public TemplateLogger templateLogger() {
        return this.logger;
    }
}
