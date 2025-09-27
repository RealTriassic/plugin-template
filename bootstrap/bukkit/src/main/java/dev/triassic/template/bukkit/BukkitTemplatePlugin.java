/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.bukkit;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplatePlugin;
import dev.triassic.template.bukkit.command.BukkitCommander;
import dev.triassic.template.command.Commander;
import dev.triassic.template.util.PlatformType;
import java.nio.file.Path;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main entry point for the plugin on the Bukkit platform.
 *
 * <p>It implements {@link TemplatePlugin}
 * to provide necessary platform-specific functionality.</p>
 */
public final class BukkitTemplatePlugin extends JavaPlugin implements TemplatePlugin {

    @Getter
    private static BukkitAudiences adventure;

    private Logger logger;
    private LegacyPaperCommandManager<Commander> commandManager;
    private TemplateImpl impl;

    /**
     * Called when the plugin is enabled.
     *
     * <p>Initializes the {@link TemplateImpl} instance for the Bukkit platform.</p>
     */
    @Override
    public void onEnable() {
        adventure = BukkitAudiences.create(this);

        this.logger = LoggerFactory.getLogger(getName());
        this.commandManager = new LegacyPaperCommandManager<>(
            this,
            ExecutionCoordinator.simpleCoordinator(),
            SenderMapper.create(
                BukkitCommander::from,
                commander -> ((BukkitCommander) commander).sender()
            )
        );

        if (this.commandManager.hasCapability(CloudBukkitCapabilities.NATIVE_BRIGADIER)) {
            this.commandManager.registerBrigadier();
        } else if (this.commandManager.hasCapability(
            CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            this.commandManager.registerAsynchronousCompletions();
        }

        this.impl = new TemplateImpl(this);
        impl.initialize();
    }

    /**
     * Called when the plugin is disabled.
     *
     * <p>Cleans up resources to increase the likelihood of a successful reload.</p>
     */
    @Override
    public void onDisable() {
        impl.shutdown();

        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
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
        return PlatformType.BUKKIT;
    }

    @Override
    public @NonNull CommandManager<Commander> commandManager() {
        return this.commandManager;
    }
}
