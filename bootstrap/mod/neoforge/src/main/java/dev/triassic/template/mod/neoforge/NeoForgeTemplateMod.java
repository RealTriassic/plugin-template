/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.mod.neoforge;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplatePlugin;
import dev.triassic.template.command.Commander;
import dev.triassic.template.mod.neoforge.command.NeoForgeCommander;
import dev.triassic.template.util.PlatformType;
import java.nio.file.Path;
import net.kyori.adventure.platform.modcommon.MinecraftServerAudiences;
import net.kyori.adventure.text.Component;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.neoforge.NeoForgeServerCommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hey!!!.
 */
@Mod("example_mod")
public final class NeoForgeTemplateMod implements TemplatePlugin {

    private static MinecraftServerAudiences adventure;

    private final Logger logger;
    private final NeoForgeServerCommandManager<Commander> commandManager;
    private TemplateImpl impl;

    /**
     * adventure.
     *
     * @return adventure instance lol.0
     */
    public static MinecraftServerAudiences adventure() {
        MinecraftServerAudiences ret = adventure;
        if (ret == null) {
            throw new IllegalStateException("Tried to access Adventure without a running server!");
        }
        return ret;
    }

    /**
     * hi!.
     */
    public NeoForgeTemplateMod() {
        NeoForge.EVENT_BUS.addListener((ServerStartingEvent e) -> {
            adventure = MinecraftServerAudiences.of(e.getServer());

            adventure.console().sendMessage(Component.text("Adventure is ready!"));
        });

        NeoForge.EVENT_BUS.addListener((ServerStoppedEvent e) -> {
            impl.shutdown();
            adventure = null;
        });

        this.logger = LoggerFactory.getLogger(NeoForgeTemplateMod.class);
        System.out.println(logger);
        logger.info("hihihihihihihihihihihihihihihihihihihihihihihihi");
        this.commandManager = new NeoForgeServerCommandManager<>(
            ExecutionCoordinator.simpleCoordinator(),
            SenderMapper.create(
                NeoForgeCommander::from,
                commander -> ((NeoForgeCommander) commander).source()
            )
        );

        this.impl = new TemplateImpl(this);
        impl.initialize();
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
