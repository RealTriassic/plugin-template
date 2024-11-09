package dev.triassic.template.localization;

import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;

public class MessageProvider {

    @Setter
    private static LocalizationCache localizationCache;

    /**
     * Retrieves and formats a localized message for the specified key using the default locale,
     * or a specified locale if provided.
     *
     * @param key    the message key
     * @param locale the locale to retrieve the message for, or null to use the default locale
     * @param args   arguments to format the message with
     * @return the formatted message or an empty string if the key is not found
     */
    public static String translate(@NonNull final String key, final Locale locale, final Object... args) {
        if (localizationCache == null)
            throw new IllegalStateException("LocalizationCache is not initialized.");

        final Optional<String> messageOpt = (locale == null)
                ? localizationCache.getString(key)
                : localizationCache.getString(key, locale);

        return messageOpt.map(message -> MessageFormat.format(message, args)).orElse("");
    }

    /**
     * Retrieves and formats a localized message for the specified key using the default locale.
     *
     * @param key  the message key
     * @param args arguments to format the message with
     * @return the formatted message or an empty string if the key is not found
     */
    public static String translate(@NonNull final String key, final Object... args) {
        return translate(key, null, args);
    }
}
