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

package dev.triassic.template;

import dev.triassic.template.configuration.Configuration;
import dev.triassic.template.configuration.ConfigurationContainer;
import dev.triassic.template.localization.LocalizationCache;
import dev.triassic.template.localization.MessageProvider;
import dev.triassic.template.util.PlatformType;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Path;

@Getter
public class TemplateImpl {

    private final PlatformType platformType;

    private final Path dataFolder;
    private final TemplateLogger logger;

    private ConfigurationContainer<Configuration> config;
    private LocalizationCache localizationCache;

    public TemplateImpl(
            final PlatformType platformType,
            final TemplateBootstrap bootstrap
    ) {
        final long startTime = System.currentTimeMillis();

        this.platformType = platformType;

        this.dataFolder = bootstrap.dataDirectory();
        this.logger = bootstrap.templateLogger();

        try {
            this.config = ConfigurationContainer.load(dataFolder, Configuration.class);
        } catch (IOException e) {
            logger.error("Failed to load configuration", e);
            return;
        }

        this.localizationCache = new LocalizationCache(this);
        MessageProvider.setLocalizationCache(localizationCache);

        logger.info(MessageProvider.translate("reloadCommandDescription"));

        logger.info("Enabled in " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
