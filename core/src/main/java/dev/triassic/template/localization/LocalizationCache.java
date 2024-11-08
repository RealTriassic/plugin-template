package dev.triassic.template.localization;

import dev.triassic.template.util.ResourceBundleUtil;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
public class LocalizationCache {

    private final Path dataDirectory;
    private final Map<String, ResourceBundle> cache = new ConcurrentHashMap<>();

    /**
     * Retrieves a localized message for the given key and locale from the cache, loading it if necessary.
     * First checks if the file exists in the data directory; falls back to resources if not found.
     *
     * @param key    the message key
     * @param locale the locale to retrieve the message for
     * @return the localized message associated with the key for the specified locale,
     * or a default message if the key is not found
     */
    public String getMessage(
            final @NonNull String key,
            final @NonNull Locale locale
    ) {
        final String localeKey = locale.toString();
        final ResourceBundle bundle = cache.computeIfAbsent(localeKey, k -> loadBundle(locale));

        return bundle.getString(key);
    }

    private ResourceBundle loadBundle(final @NonNull Locale locale) {
        final Path path = dataDirectory.resolve("messages")
                .resolve("messages_" + locale + ".properties");

        return ResourceBundleUtil.loadResourceBundle(path, "messages", locale);
    }

    /**
     * Clears all cached resource bundles, forcing reloading on subsequent requests.
     */
    public void clear() {
        cache.clear();
    }
}
