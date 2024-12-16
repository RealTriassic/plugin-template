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

package dev.triassic.template.localization;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.util.ResourceBundleUtil;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Caches localized messages for different locales, loading them from the specified directory.
 * It retrieves resource bundles for the default locale and other locales as needed.
 */
public class LocalizationCache {

    private final Path messagesDir;
    private final Locale defaultLocale = Locale.getDefault();
    private final Map<Locale, ResourceBundle> cache = new ConcurrentHashMap<>();

    /**
     * Constructs a new {@link LocalizationCache} instance.
     *
     * @param instance the {@link TemplateImpl} instance
     */
    public LocalizationCache(TemplateImpl instance) {
        this.messagesDir = instance.getDataDirectory().resolve("messages");
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
        return bundle != null && bundle.containsKey(key)
            ? Optional.of(bundle.getString(key))
            : Optional.empty();
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
