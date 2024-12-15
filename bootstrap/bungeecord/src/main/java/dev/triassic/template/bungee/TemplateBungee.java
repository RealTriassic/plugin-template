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

package dev.triassic.template.bungee;

import dev.triassic.template.TemplateBootstrap;
import dev.triassic.template.TemplateImpl;
import dev.triassic.template.bungee.command.BungeeCommander;
import dev.triassic.template.command.Commander;
import dev.triassic.template.util.PlatformType;
import java.nio.file.Path;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.CommandManager;
import org.incendo.cloud.SenderMapper;
import org.incendo.cloud.bungee.BungeeCommandManager;
import org.incendo.cloud.execution.ExecutionCoordinator;

/**
 * The main entry point for the plugin on the Bungeecord platform.
 *
 * <p>It implements {@link TemplateBootstrap}
 * to provide necessary platform-specific functionality.</p>
 */
public class TemplateBungee extends Plugin implements TemplateBootstrap {

    private static BungeeAudiences adventure;
    private BungeeCommandManager<Commander> commandManager;

    /**
     * Retrieves the shared {@link BungeeAudiences} instance.
     *
     * @return the {@link BungeeAudiences} instance
     */
    public static @NonNull BungeeAudiences adventure() {
        if (adventure == null) {
            throw new IllegalStateException(
                "Tried to access Adventure when the plugin was disabled!");
        }
        return adventure;
    }

    /**
     * Called when the plugin is enabled.
     *
     * <p>Initializes the {@link TemplateImpl} instance for the Bungeecord platform.</p>
     */
    @Override
    public void onEnable() {
        adventure = BungeeAudiences.create(this);

        this.commandManager = new BungeeCommandManager<>(
            this,
            ExecutionCoordinator.simpleCoordinator(),
            SenderMapper.create(
                BungeeCommander::from,
                commander -> ((BungeeCommander) commander).sender()
            )
        );

        new TemplateImpl(this);
    }

    /**
     * Called when the plugin is disabled.
     *
     * <p>Cleans up resources to increase the likelihood of a successful {@code /reload}.</p>
     */
    @Override
    public void onDisable() {
        if (adventure != null) {
            adventure.close();
            adventure = null;
        }
    }

    @Override
    public @NonNull Path dataDirectory() {
        return this.getDataFolder().toPath();
    }

    @Override
    public @NonNull PlatformType platformType() {
        return PlatformType.BUNGEECORD;
    }

    @Override
    public @NonNull CommandManager<Commander> commandManager() {
        return this.commandManager;
    }
}
