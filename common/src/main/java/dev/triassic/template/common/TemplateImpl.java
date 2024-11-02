package dev.triassic.template.common;

import dev.triassic.template.common.configuration.Configuration;
import dev.triassic.template.common.configuration.ConfigurationContainer;
import dev.triassic.template.common.util.PlatformType;
import lombok.Getter;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Path;

@Getter
public class TemplateImpl {

    private final PlatformType platformType;

    private final Logger logger;
    private final Path dataFolder;

    private ConfigurationContainer<Configuration> config;

    public TemplateImpl(
            final PlatformType platformType,
            final TemplateBootstrap bootstrap
    ) {
        long startTime = System.currentTimeMillis();

        this.platformType = platformType;

        this.logger = bootstrap.logger();
        this.dataFolder = bootstrap.dataFolder();

        try {
            this.config = ConfigurationContainer.load(dataFolder, Configuration.class);
        } catch (IOException e) {
            logger.error("Failed to load configuration", e);
            return;
        }

        logger.info("Enabled in {}ms", System.currentTimeMillis() - startTime);
    }
}
