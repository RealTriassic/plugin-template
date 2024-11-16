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

package dev.triassic.template.bukkit;

import dev.triassic.template.TemplateBootstrap;
import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplateLogger;
import dev.triassic.template.util.PlatformType;
import java.nio.file.Path;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class for the Template plugin on Bukkit.
 */
public class TemplateBukkit extends JavaPlugin implements TemplateBootstrap {

    private final TemplateBukkitLogger logger = new TemplateBukkitLogger(getLogger());

    /**
     * Called when the plugin is enabled.
     *
     * <p>Initializes the {@link TemplateImpl} instance for the Bukkit platform.</p>
     */
    @Override
    public void onEnable() {
        new TemplateImpl(PlatformType.BUKKIT, this);
    }

    /**
     * Gets the data directory of the plugin.
     *
     * @return the data directory path
     */
    public Path dataDirectory() {
        return this.getDataFolder().toPath();
    }

    /**
     * Gets the logger of the plugin.
     *
     * @return the logger
     */
    public TemplateLogger templateLogger() {
        return this.logger;
    }
}
