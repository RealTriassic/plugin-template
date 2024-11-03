package dev.triassic.template.velocity;

import dev.triassic.template.common.TemplateLogger;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

@RequiredArgsConstructor
public class TemplateVelocityLogger implements TemplateLogger {

    private final Logger logger;

    @Override
    public void severe(String message) {
        logger.error(message);
    }

    @Override
    public void severe(String message, Throwable error) {
        logger.error(message, error);
    }

    @Override
    public void error(String message) {
        severe(message);
    }

    @Override
    public void error(String message, Throwable error) {
        severe(message, error);
    }

    @Override
    public void warning(String message) {
        logger.warn(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }
}
