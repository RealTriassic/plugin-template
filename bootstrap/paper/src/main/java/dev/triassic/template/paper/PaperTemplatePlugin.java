/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.paper;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplatePlugin;
import dev.triassic.template.command.Commander;
import dev.triassic.template.paper.command.PaperCommander;
import dev.triassic.template.util.PlatformType;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import java.nio.file.Path;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.PaperCommandManager;
import org.slf4j.Logger;

/**
 * The main entry point for the plugin on the Paper platform.
 *
 * <p>It implements {@link TemplatePlugin}
 * to provide necessary platform-specific functionality.</p>
 */
public final class PaperTemplatePlugin extends JavaPlugin implements TemplatePlugin {

    private Logger logger;
    private PaperCommandManager<Commander> commandManager;
    private TemplateImpl impl;

    @Override
    public void onEnable() {
        this.logger = this.getSLF4JLogger();

        final SenderMapper<CommandSourceStack, Commander>
            mapper = SenderMapper.create(
                PaperCommander::from, commander -> ((PaperCommander) commander).source());

        this.commandManager = PaperCommandManager.builder(mapper)
            .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
            .buildOnEnable(this);

        this.impl = new TemplateImpl(this);
        impl.initialize();
    }

    /**
     * Called when the plugin is disabled.
     */
    @Override
    public void onDisable() {
        impl.shutdown();
    }

    @Override
    public @NonNull Logger logger() {
        return this.logger;
    }

    @Override
    public @NonNull Path dataDirectory() {
        return this.getDataFolder().toPath();
    }

    @Override
    public @NonNull PlatformType platformType() {
        return PlatformType.PAPER;
    }

    @Override
    public @NonNull CommandManager<Commander> commandManager() {
        return this.commandManager;
    }
}
