/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.mod.fabric;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplatePlugin;
import dev.triassic.template.command.Commander;
import dev.triassic.template.mod.fabric.command.FabricCommander;
import dev.triassic.template.util.PlatformType;
import java.nio.file.Path;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.fabric.FabricServerCommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * hey!!!.
 */
public final class FabricTemplateMod implements ModInitializer, TemplatePlugin {

    private Logger logger;
    private FabricServerCommandManager<Commander> commandManager;
    private TemplateImpl impl;

    @Override
    public void onInitialize() {
        this.logger = LoggerFactory.getLogger(FabricTemplateMod.class);
        this.commandManager = new FabricServerCommandManager<>(
            ExecutionCoordinator.simpleCoordinator(),
            SenderMapper.create(
                FabricCommander::from,
                commander -> ((FabricCommander) commander).source()
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
