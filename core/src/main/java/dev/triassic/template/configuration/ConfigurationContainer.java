package dev.triassic.template.configuration;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.yaml.NodeStyle;
import org.spongepowered.configurate.yaml.YamlConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicReference;

public final class ConfigurationContainer<C> {

    private static final String HEADER = """
            TemplatePlugin Configuration File
            A multi-platform template plugin for Minecraft servers.
            
            Report any issues on our GitHub repository:
            https://github.com/RealTriassic/plugin-template""";

    private final Class<C> clazz;
    private final AtomicReference<C> config;
    private final YamlConfigurationLoader loader;

    private ConfigurationContainer(
            final C config,
            final Class<C> clazz,
            final YamlConfigurationLoader loader
    ) {
        this.clazz = clazz;
        this.loader = loader;
        this.config = new AtomicReference<>(config);
    }

    public static <C> ConfigurationContainer<C> load(
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

        return new ConfigurationContainer<>(config, clazz, loader);
    }

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

    public C get() {
        return config.get();
    }
}
