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
