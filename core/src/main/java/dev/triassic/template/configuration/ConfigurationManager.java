/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.configuration;

import dev.triassic.template.BuildParameters;
import dev.triassic.template.annotation.ExcludePlatform;
import dev.triassic.template.util.PlatformType;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicReference;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.objectmapping.meta.Processor;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

/**
 * A manager for managing the plugin's configuration.
 *
 * <p>Provides methods to load, reload, and access the configuration data.</p>
 *
 * @param <T> the type of the configuration object
 */
public record ConfigurationManager<T>(
    Class<T> configClass,
    YamlConfigurationLoader loader,
    AtomicReference<T> config
) {

    private static final String HEADER = String.format(
        """
        %s Configuration File
        %s
        
        Report any issues on our GitHub repository:
        %s
        """, BuildParameters.NAME, BuildParameters.DESCRIPTION, BuildParameters.URL);

    /**
     * Loads the configuration from the path and
     * creates a new {@link ConfigurationManager} instance.
     *
     * <p>If the configuration file does not exist, it'll create a new one with default values.</p>
     *
     * @param path        the path to the directory containing the configuration file
     * @param configClass the class type of the configuration object
     * @param <T>         the type of the configuration object
     * @return a {@link ConfigurationManager} instance containing the loaded configuration
     * @throws IOException if an error occurs while loading the configuration
     */
    public static <T> ConfigurationManager<T> load(
        Path path,
        Class<T> configClass,
        PlatformType platformType
    ) throws IOException {
        path = path.resolve("config.yml");

        ObjectMapper.Factory mapperFactory = ObjectMapper.factoryBuilder()
            .addProcessor(ExcludePlatform.class, excludePlatform(platformType))
            .build();

        YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
            .path(path)
            .indent(2)
            .nodeStyle(NodeStyle.BLOCK)
            .defaultOptions(options -> options
                .header(HEADER)
                .serializers(builder ->
                    builder.registerAnnotatedObjects(mapperFactory))
            )
            .build();

        CommentedConfigurationNode root = loader.load();
        T config = root.get(configClass);

        if (Files.notExists(path)) {
            loader.save(root);
        }

        return new ConfigurationManager<>(configClass, loader, new AtomicReference<>(config));
    }

    /**
     * Asynchronously reloads the configuration from disk.
     *
     * <p>The current configuration object is updated with the newly loaded data.</p>
     *
     * @return a {@link CompletableFuture} that completes when the reload is complete
     */
    public CompletableFuture<Void> reload() {
        return CompletableFuture.runAsync(() -> {
            try {
                CommentedConfigurationNode root = loader.load();
                config.set(root.get(configClass));
            } catch (ConfigurateException e) {
                throw new CompletionException("Failed to load configuration", e);
            }
        });
    }

    /**
     * Gets the current configuration object.
     *
     * @return the current configuration object
     */
    public T get() {
        return config.get();
    }

    private static Processor.Factory<ExcludePlatform, Object> excludePlatform(
        PlatformType platformType
    ) {
        return (annotation, fieldType) -> (value, destination) -> {
            for (PlatformType platform : annotation.value()) {
                if (platformType.equals(platform)) {
                    // noinspection DataFlowIssue
                    destination.parent().removeChild(destination.key());
                    break;
                }
            }
        };
    }
}
