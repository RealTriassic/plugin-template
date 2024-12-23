/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <https://unlicense.org/>
 */

package dev.triassic.template.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicReference;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

/**
 * A manager for managing the plugin's configuration.
 *
 * <p>This class provides methods to load, reload, and access the configuration data.</p>
 *
 * @param <C> the type of the configuration object
 */
public class ConfigurationManager<C> {

    private static final String HEADER = """
            TemplatePlugin Configuration File
            A multi-platform template plugin for Minecraft servers.
            
            Report any issues on our GitHub repository:
            https://github.com/RealTriassic/plugin-template""";

    private final Class<C> clazz;
    private final AtomicReference<C> config;
    private final YamlConfigurationLoader loader;

    /**
     * Creates a {@link ConfigurationManager} with the
     * given configuration, class type, and loader.
     *
     * @param config the initial configuration
     * @param clazz the class type of the configuration object
     * @param loader the {@link YamlConfigurationLoader}
     */
    private ConfigurationManager(
            final C config,
            final Class<C> clazz,
            final YamlConfigurationLoader loader
    ) {
        this.clazz = clazz;
        this.loader = loader;
        this.config = new AtomicReference<>(config);
    }

    /**
     * Loads the configuration from the path and
     * creates a new {@link ConfigurationManager} instance.
     * If the configuration file does not exist, it will create a new one with default values.
     *
     * @param path the path to the configuration file
     * @param clazz the class type of the configuration object
     * @param <C> the type of the configuration object
     * @return a {@link ConfigurationManager} instance containing the loaded configuration
     * @throws IOException if an error occurs while loading the configuration
     */
    public static <C> ConfigurationManager<C> load(
            Path path,
            final Class<C> clazz
    ) throws IOException {
        path = path.resolve("config.yml");

        final YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
                .indent(2)
                .path(path)
                .nodeStyle(NodeStyle.BLOCK)
                .defaultOptions(options -> options
                        .shouldCopyDefaults(true)
                        .header(HEADER))
                .build();

        final CommentedConfigurationNode node = loader.load();
        final C config = node.get(clazz);

        if (Files.notExists(path)) {
            node.set(clazz, config);
            loader.save(node);
        }

        return new ConfigurationManager<>(config, clazz, loader);
    }

    /**
     * Asynchronously reloads the configuration from disk.
     * The current configuration object is updated with the newly loaded data.
     *
     * @return a {@link CompletableFuture} that completes when the reload is complete
     */
    public CompletableFuture<Void> reload() {
        return CompletableFuture.runAsync(() -> {
            try {
                final CommentedConfigurationNode node = loader.load();
                config.set(node.get(clazz));
            } catch (ConfigurateException e) {
                throw new CompletionException("Failed to load configuration", e);
            }
        });
    }

    /**
     * Gets the current configuration object.
     *
     * @return the current configuration
     */
    public C get() {
        return config.get();
    }
}
