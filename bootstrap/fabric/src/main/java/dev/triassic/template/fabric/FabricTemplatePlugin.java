/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.fabric;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplatePlugin;
import dev.triassic.template.command.Commander;
import dev.triassic.template.fabric.command.FabricCommander;
import dev.triassic.template.util.PlatformType;
import java.nio.file.Path;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.MinecraftServer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.fabric.FabricServerCommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main entry point for the plugin on the Fabric platform.
 *
 * <p>It implements {@link TemplatePlugin}
 * to provide necessary platform-specific functionality.</p>
 */
public final class FabricTemplatePlugin implements TemplatePlugin, DedicatedServerModInitializer {

    private Logger logger;
    private FabricServerCommandManager<Commander> commandManager;
    private TemplateImpl impl;

    @Override
    public void onInitializeServer() {
        this.logger = LoggerFactory.getLogger(FabricTemplatePlugin.class);
        this.commandManager = new FabricServerCommandManager<>(
            ExecutionCoordinator.simpleCoordinator(),
            SenderMapper.create(
                FabricCommander::from,
                commander -> ((FabricCommander) commander).source()
            )
        );

        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPING.register(this::onServerStopping);
    }

    /**
     * Called when the server is starting.
     *
     * <p>Initializes the {@link TemplateImpl} instance for the Fabric platform.</p>
     */
    private void onServerStarting(final MinecraftServer server) {
        this.impl = new TemplateImpl(this);
        impl.initialize();
    }

    /**
     * Called when the server is stopping.
     */
    private void onServerStopping(final MinecraftServer server) {
        if (impl != null) {
            impl.shutdown();
        }
    }

    @Override
    public @NonNull Logger logger() {
        return logger;
    }

    @Override
    public @NonNull Path dataDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public @NonNull PlatformType platformType() {
        return PlatformType.FABRIC;
    }

    @Override
    public @NonNull CommandManager<Commander> commandManager() {
        return this.commandManager;
    }
}
