package dev.triassic.template.localization;

import dev.triassic.template.TemplateImpl;
import dev.triassic.template.TemplateLogger;
import dev.triassic.template.util.ResourceBundleUtil;

import java.nio.file.Path;
import java.util.*;

public class LocalizationCache {

    private final Locale defaultLocale;
    private final Path messagesDir;
    private final Map<Locale, ResourceBundle> cache = new HashMap<>();
    private final TemplateLogger logger;

    /**
     * Initializes the LocalizationCache with default locale, messages directory, and logger.
     *
     * @param instance      the main application instance
     * @param defaultLocale the default locale for retrieving messages
     */
    public LocalizationCache(TemplateImpl instance, Locale defaultLocale) {
        this.messagesDir = instance.getDataFolder().resolve("messages");
        this.logger = instance.getLogger();
        this.defaultLocale = defaultLocale;
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
     * Clears all cached resource bundles, forcing reloading on subsequent requests.
     */
    public void resetCache() {
        cache.clear();
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

