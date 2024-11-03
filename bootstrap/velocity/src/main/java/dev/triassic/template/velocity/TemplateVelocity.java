package dev.triassic.template.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import dev.triassic.template.common.TemplateBootstrap;
import dev.triassic.template.common.TemplateImpl;
import dev.triassic.template.common.TemplateLogger;
import dev.triassic.template.common.util.PlatformType;
import org.slf4j.Logger;

import java.nio.file.Path;

@Plugin(
        id = "templateplugin",
        name = "TemplatePlugin",
        version = "1.0.0"
)
public class TemplateVelocity implements TemplateBootstrap {

    @Inject
    private Logger slf4jLogger;
    @DataDirectory
    private Path dataFolder;

    private final TemplateVelocityLogger logger = new TemplateVelocityLogger(slf4jLogger);

    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {
        new TemplateImpl(PlatformType.VELOCITY, this);
    }

    public Path dataDirectory() {
        return this.dataFolder;
    }

    public TemplateLogger templateLogger() {
        return this.logger;
    }
}
