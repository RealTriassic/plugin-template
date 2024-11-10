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
