package dev.triassic.template.util;

import java.util.Locale;

public class LocaleUtil {

    /**
     * Converts a locale string (e.g., "en_US") to a Locale object.
     *
     * @param localeString the locale string to convert
     * @return the corresponding Locale object, or the default locale if the string is invalid
     */
    public static Locale fromString(String localeString) {
        return localeString == null || localeString.isEmpty()
                ? Locale.getDefault()
                : Locale.forLanguageTag(localeString.replace('_', '-'));
    }
}
