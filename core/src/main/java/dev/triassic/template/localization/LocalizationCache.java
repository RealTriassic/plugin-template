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
package dev.triassic.template.localization;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.util.ResourceBundleUtil;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

public class LocalizationCache {

    private final Locale defaultLocale = Locale.getDefault();
    private final Path messagesDir;
    private final Map<Locale, ResourceBundle> cache = new ConcurrentHashMap<>();

    public LocalizationCache(TemplateImpl instance) {
        this.messagesDir = instance.getDataFolder().resolve("messages");
    }

    /**
     * Retrieves a localized string for the given key using the default locale.
     *
     * @param key the key of the message
     * @return an Optional containing the localized message if found, or empty if not
     */
    public Optional<String> getString(String key) {
        return getString(key, defaultLocale);
    }

    /**
     * Retrieves a localized string for the given key and specified locale.
     *
     * @param key    the key of the message
     * @param locale the locale to retrieve the message for
     * @return an Optional containing the localized message if found, or empty if not
     */
    public Optional<String> getString(String key, Locale locale) {
        ResourceBundle bundle = cache.computeIfAbsent(locale, this::loadBundle);
        return bundle != null && bundle.containsKey(key) ? Optional.of(bundle.getString(key)) : Optional.empty();
    }

    /**
     * Loads a ResourceBundle for a given locale.
     *
     * @param locale the locale to load the ResourceBundle for
     * @return the loaded ResourceBundle or null if loading fails
     */
    private ResourceBundle loadBundle(Locale locale) {
        return ResourceBundleUtil.loadBundle("messages", messagesDir, locale);
    }
}
