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
