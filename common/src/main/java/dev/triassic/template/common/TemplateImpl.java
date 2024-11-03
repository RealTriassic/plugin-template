package dev.triassic.template.common;

import dev.triassic.template.common.configuration.Configuration;
import dev.triassic.template.common.configuration.ConfigurationContainer;
import dev.triassic.template.common.util.PlatformType;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Path;

@Getter
public final class TemplateImpl {

    private final PlatformType platformType;

    private final Path dataFolder;
    private final TemplateLogger logger;

    private ConfigurationContainer<Configuration> config;

    public TemplateImpl(
            final PlatformType platformType,
            final TemplateBootstrap bootstrap
    ) {
        long startTime = System.currentTimeMillis();

        this.platformType = platformType;

        this.dataFolder = bootstrap.dataDirectory();
        this.logger = bootstrap.templateLogger();

        try {
            this.config = ConfigurationContainer.load(dataFolder, Configuration.class);
        } catch (IOException e) {
            logger.error("Failed to load configuration", e);
            return;
        }

        logger.info("Enabled in " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
