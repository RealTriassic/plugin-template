package dev.triassic.template;

import java.nio.file.Path;

public interface TemplateBootstrap {

    /**
     * Gets the path to the platform's data directory.
     *
     * @return the path to the data directory
     */
    Path dataDirectory();

    /**
     * Gets the platform's logger instance.
     *
     * @return the logger instance
     */
    TemplateLogger templateLogger();
}
