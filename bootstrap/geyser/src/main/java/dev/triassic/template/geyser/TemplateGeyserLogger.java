package dev.triassic.template.geyser;

import dev.triassic.template.common.TemplateLogger;
import lombok.RequiredArgsConstructor;
import org.geysermc.geyser.api.extension.ExtensionLogger;

@RequiredArgsConstructor
public class TemplateGeyserLogger implements TemplateLogger {

    private final ExtensionLogger logger;

    @Override
    public void severe(String message) {
        logger.severe(message);
    }

    @Override
    public void severe(String message, Throwable error) {
        logger.severe(message, error);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void error(String message, Throwable error) {
        logger.error(message, error);
    }

    @Override
    public void warning(String message) {
        logger.warning(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }
}
