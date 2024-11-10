/*
 * MIT License
 *
 * Copyright (c) 2024 Triassic
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

public class ConfigurationContainer<C> {

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
