package dev.triassic.template.common;

import java.nio.file.Path;

public interface TemplateBootstrap {

    /**
     * Gets the path to the platform's data folder.
     *
     * @return the path to the data folder
     */
    Path templateDataFolder();

    /**
     * Gets the platform's logger instance.
     *
     * @return the logger instance
     */
    TemplateLogger templateLogger();
}
