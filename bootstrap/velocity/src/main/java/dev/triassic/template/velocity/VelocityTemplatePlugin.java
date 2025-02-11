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

package dev.triassic.template.velocity;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import dev.triassic.template.BuildParameters;
import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplatePlugin;
import dev.triassic.template.command.Commander;
import dev.triassic.template.util.PlatformType;
import dev.triassic.template.velocity.command.VelocityCommander;
import java.nio.file.Path;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.velocity.CloudInjectionModule;
import org.incendo.cloud.velocity.VelocityCommandManager;
import org.slf4j.Logger;

/**
 * The main entry point for the plugin on the Velocity platform.
 *
 * <p>It implements {@link TemplatePlugin}
 * to provide necessary platform-specific functionality.</p>
 */
@Plugin(
    id = BuildParameters.NAME,
    version = BuildParameters.VERSION,
    description = BuildParameters.DESCRIPTION,
    url = BuildParameters.URL,
    authors = BuildParameters.AUTHOR
)
public final class VelocityTemplatePlugin implements TemplatePlugin {

    private final Logger logger;
    private final Path dataDirectory;

    @Inject
    private Injector injector;
    private VelocityCommandManager<Commander> commandManager;
    private TemplateImpl impl;

    /**
     * Initializes a new {@link VelocityTemplatePlugin} instance.
     *
     * @param dataDirectory the path to the data directory
     */
    @Inject
    public VelocityTemplatePlugin(
        final Logger logger,
        final @DataDirectory Path dataDirectory
    ) {
        this.logger = logger;
        this.dataDirectory = dataDirectory;
    }

    /**
     * Called when the plugin is enabled.
     *
     * <p>Initializes the {@link TemplateImpl} instance for the Velocity platform.</p>
     */
    @Subscribe
    public void onEnable(final ProxyInitializeEvent event) {
        this.commandManager = injector.createChildInjector(
            new CloudInjectionModule<>(
                Commander.class,
                ExecutionCoordinator.simpleCoordinator(),
                SenderMapper.create(
                    VelocityCommander::from,
                    commander -> ((VelocityCommander) commander).sender()
                )
            )
        ).getInstance(Key.get(new TypeLiteral<>() {}));

        this.impl = new TemplateImpl(this);
        impl.initialize();
    }

    /**
     * Called when the plugin is disabled.
     */
    @Subscribe
    public void onDisable(final ProxyShutdownEvent event) {
        impl.shutdown();
    }

    @Override
    public @NonNull Logger logger() {
        return this.logger;
    }

    @Override
    public @NonNull Path dataDirectory() {
        return this.dataDirectory;
    }

    @Override
    public @NonNull PlatformType platformType() {
        return PlatformType.VELOCITY;
    }

    @Override
    public @NonNull CommandManager<Commander> commandManager() {
        return this.commandManager;
    }
}
