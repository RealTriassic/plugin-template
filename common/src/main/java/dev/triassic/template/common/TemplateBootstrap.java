package dev.triassic.template.common;

import org.slf4j.Logger;

import java.nio.file.Path;

public interface TemplateBootstrap {

    /**
     * Gets the path to the platform's data folder.
     *
     * @return the path to the data folder
     */
    Path dataFolder();

    /**
     * Gets the platform's logger instance.
     *
     * @return the logger instance
     */
    Logger logger();
}
