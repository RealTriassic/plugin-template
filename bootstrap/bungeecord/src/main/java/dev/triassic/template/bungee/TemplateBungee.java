/*
 * MIT License
 *
 * Copyright (c) 2024 Triassic
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.triassic.template.bungee;

import dev.triassic.template.TemplateBootstrap;
import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplateLogger;
import dev.triassic.template.util.PlatformType;
import java.nio.file.Path;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Main class for the Template plugin on Bungeecord.
 */
public class TemplateBungee extends Plugin implements TemplateBootstrap {

    private final TemplateBungeeLogger logger = new TemplateBungeeLogger(getLogger());

    /**
     * Called when the plugin is enabled.
     *
     * <p>Initializes the {@link TemplateImpl} instance for the Bungeecord platform.</p>
     */
    @Override
    public void onEnable() {
        new TemplateImpl(PlatformType.BUNGEECORD, this);
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
     * Gets the logger for the template plugin.
     *
     * @return the template logger
     */
    public TemplateLogger templateLogger() {
        return this.logger;
    }
}
