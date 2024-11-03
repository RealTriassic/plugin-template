package dev.triassic.template.bukkit;

import dev.triassic.template.common.TemplateLogger;
import lombok.RequiredArgsConstructor;

import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class TemplateBukkitLogger implements TemplateLogger {

    private final Logger logger;

    @Override
    public void severe(String message) {
        logger.severe(message);
    }

    @Override
    public void severe(String message, Throwable error) {
        logger.log(Level.SEVERE, message, error);
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
        logger.warning(message);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }
}
