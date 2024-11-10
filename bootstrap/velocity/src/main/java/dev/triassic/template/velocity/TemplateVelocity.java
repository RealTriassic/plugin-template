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
import org.slf4j.Logger;

import java.nio.file.Path;

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

    @Subscribe
    public void onEnable(ProxyInitializeEvent event) {
        new TemplateImpl(PlatformType.VELOCITY, this);
    }

    public Path dataDirectory() {
        return this.dataFolder;
    }

    public TemplateLogger templateLogger() {
        return this.logger;
    }
}
