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

package dev.triassic.template.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import dev.triassic.template.TemplateBootstrap;
import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplateLogger;
import dev.triassic.template.util.PlatformType;
import java.nio.file.Path;
import org.slf4j.Logger;

/**
 * Main class for the Template plugin on Velocity.
 */
@Plugin(
        id = "templateplugin",
        name = "TemplatePlugin",
        version = "1.0.0"
)
public class TemplateVelocity implements TemplateBootstrap {

    @Inject
    private Logger slf4jLogger;
    @DataDirectory
    private Path dataFolder;

    private final TemplateVelocityLogger logger = new TemplateVelocityLogger(slf4jLogger);

    /**
     * Called when the plugin is enabled.
     *
     * <p>Initializes the {@link TemplateImpl} instance for the Velocity platform.</p>
     */
    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {
        new TemplateImpl(PlatformType.VELOCITY, this);
    }

    /**
     * Gets the data directory of the plugin.
     *
     * @return the data directory path
     */
    public Path dataDirectory() {
        return this.dataFolder;
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
