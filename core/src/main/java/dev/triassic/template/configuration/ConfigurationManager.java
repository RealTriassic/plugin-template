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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.objectmapping.ObjectMapper;
import org.spongepowered.configurate.objectmapping.meta.Processor;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

/**
 * A manager for managing the plugin's configuration.
 *
 * <p>This class provides methods to load, reload, and access the configuration data.</p>
 *
 * @param <T> the type of the configuration object
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfigurationManager<T> {

    private static final String HEADER = String.format("""
            %s Configuration File
            %s
            
            Report any issues on our GitHub repository:
            %s""", BuildParameters.NAME, BuildParameters.DESCRIPTION, BuildParameters.URL);

    private final Class<T> clazz;
    private final YamlConfigurationLoader loader;
    private final AtomicReference<T> config;

    /**
     * Loads the configuration from the path and
     * creates a new {@link ConfigurationManager} instance.
     * If the configuration file does not exist, it will create a new one with default values.
     *
     * @param path the path to the directory containing the configuration file
     * @param clazz the class type of the configuration object
     * @param <T> the type of the configuration object
     * @return a {@link ConfigurationManager} instance containing the loaded configuration
     * @throws IOException if an error occurs while loading the configuration
     */
    public static <T> ConfigurationManager<T> load(
        Path path,
        final Class<T> clazz,
        final PlatformType platformType
    ) throws IOException {
        path = path.resolve("config.yml");

        final YamlConfigurationLoader loader = YamlConfigurationLoader.builder()
            .indent(2)
            .nodeStyle(NodeStyle.BLOCK)
            .defaultOptions(opts -> opts
                .header(HEADER)
                .serializers(TypeSerializerCollection.defaults().childBuilder()
                    .registerAnnotatedObjects(ObjectMapper.factoryBuilder()
                        .addProcessor(ExcludePlatform.class, excludePlatform(platformType))
                        .build()
                    )
                    .build()))
            .path(path)
            .build();

        final CommentedConfigurationNode root = loader.load();
        final T config = root.get(clazz);

        if (Files.notExists(path)) {
            loader.save(root);
        }

        return new ConfigurationManager<>(clazz, loader, new AtomicReference<>(config));
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
                final CommentedConfigurationNode root = loader.load();
                config.set(root.get(clazz));
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
        final PlatformType platformType
    ) {
        return (annotation, fieldType) -> (value, destination) -> {
            for (PlatformType platform : annotation.value()) {
                if (platformType.equals(platform)) {
                    destination.parent().removeChild(destination.key());
                    break;
                }
            }
        };
    }
}
