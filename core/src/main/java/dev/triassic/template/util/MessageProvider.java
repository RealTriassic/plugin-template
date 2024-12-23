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

package dev.triassic.template.util;

import dev.triassic.template.localization.LocalizationCache;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;
import lombok.Setter;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides static methods for retrieving and formatting localized messages from a cache.
 */
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
    public static String translate(
        @NonNull final String key,
        final Locale locale,
        final Object... args
    ) {
        if (localizationCache == null) {
            throw new IllegalStateException("LocalizationCache is not initialized.");
        }

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
