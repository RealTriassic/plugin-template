/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.neoforge;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplatePlugin;
import dev.triassic.template.command.Commander;
import dev.triassic.template.neoforge.command.NeoForgeCommander;
import dev.triassic.template.util.PlatformType;
import java.nio.file.Path;
import lombok.Getter;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.neoforge.NeoForgeServerCommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main entry point for the plugin on the NeoForge platform.
 *
 * <p>It implements {@link TemplatePlugin}
 * to provide necessary platform-specific functionality.</p>
 */
@Mod("example_mod")
public final class NeoForgeTemplatePlugin implements TemplatePlugin {

    @Getter
    private static MinecraftServerAudiences adventure;

    private final Logger logger;
    private final NeoForgeServerCommandManager<Commander> commandManager;
    private TemplateImpl impl;

    /**
     * hi!.
     */
    public NeoForgeTemplatePlugin() {
        this.logger = LoggerFactory.getLogger(NeoForgeTemplatePlugin.class);
        this.commandManager = new NeoForgeServerCommandManager<>(
            ExecutionCoordinator.simpleCoordinator(),
            SenderMapper.create(
                NeoForgeCommander::from,
                commander -> ((NeoForgeCommander) commander).source()
            )
        );

        NeoForge.EVENT_BUS.addListener(this::onServerStarting);
        NeoForge.EVENT_BUS.addListener(this::onServerStopping);
    }

    /**
     * Called when the server is starting.
     *
     * <p>Initializes the {@link TemplateImpl} instance for the NeoForge platform.</p>
     */
    private void onServerStarting(final ServerStartingEvent event) {
        this.impl = new TemplateImpl(this);
        impl.initialize();
    }

    /**
     * Called when the server is stopping.
     */
    private void onServerStopping(final ServerStoppingEvent event) {
        if (impl != null) {
            impl.shutdown();
        }

        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    @Override
    public @NonNull Logger logger() {
        return logger;
    }

    @Override
    public @NonNull Path dataDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public @NonNull PlatformType platformType() {
        return PlatformType.NEOFORGE;
    }

    @Override
    public @NonNull CommandManager<Commander> commandManager() {
        return this.commandManager;
    }
}