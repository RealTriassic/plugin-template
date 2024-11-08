package dev.triassic.template.localization;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Locale;

public class MessageProvider {

    private static LocalizationCache cache;

    /**
     * Sets the shared LocalizationCache instance.
     *
     * @param cache the LocalizationCache instance to be shared across all calls
     */
    public static void setCache(final @NonNull LocalizationCache cache) {
        MessageProvider.cache = cache;
    }

    /**
     * Retrieves a formatted message by key for the specified locale and formats it with any additional arguments.
     *
     * @param key    the key of the message
     * @param locale the locale for which the message is requested
     * @param args   the arguments to format the message with
     * @return the formatted localized message
     */
    public static String getMessage(
            final @NonNull String key,
            final @NonNull Locale locale,
            final Object... args
    ) {
        if (cache == null)
            throw new IllegalStateException("LocalizationCache is not initialized. Call setCache() first");

        String message = cache.getMessage(key, locale);
        return formatMessage(message, args);
    }

    private static String formatMessage(final @NonNull String message, final Object... args) {
        return String.format(message, args);
    }
}
