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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Utility class for loading resource bundles, prioritizing properties from a local file.
 * If the local file is not available, it falls back to loading from the classpath resources.
 */
public class ResourceBundleUtil {

    /**
     * Loads a resource bundle for the given locale, prioritizing properties from a local file.
     * If the local file does not exist, it falls back to the classpath resources.
     *
     * @param baseName the base name of the resource bundle
     * @param path     the path to the local folder containing messages files
     * @param locale   the locale for the resource bundle
     * @return a merged resource bundle, with local properties overriding classpath properties
     */
    public static @Nullable ResourceBundle loadBundle(
            final @NonNull String baseName,
            final @NonNull Path path,
            final @NonNull Locale locale
    ) {
        return ResourceBundle.getBundle(baseName, locale, new FileControl(path));
    }

    /**
     * Custom {@link ResourceBundle.Control} implementation to load
     * resources from both the classpath and local files.
     */
    @RequiredArgsConstructor
    private static class FileControl extends ResourceBundle.Control {
        private @NonNull Path path;

        @Override
        public @Nullable ResourceBundle newBundle(String baseName, Locale locale, String format,
                                                  ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            if (!"java.properties".equals(format)) {
                return super.newBundle(baseName, locale, format, loader, reload);
            }

            final String bundleName = toBundleName(baseName, locale);
            final String resourceName = toResourceName(bundleName, "properties");

            PropertyResourceBundle resourceBundle = null;
            try (final InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                if (resourceStream != null) {
                    resourceBundle = new PropertyResourceBundle(resourceStream);
                }

                path = path.resolve(resourceName);
                if (Files.exists(path)) {
                    try (final InputStream localStream = Files.newInputStream(path)) {
                        final PropertyResourceBundle localBundle =
                            new PropertyResourceBundle(localStream);
                        if (resourceBundle != null) {
                            return new MergedResourceBundle(localBundle, resourceBundle);
                        }

                        return localBundle;
                    }
                }

                return resourceBundle;
            }
        }
    }

    /**
     * A merged resource bundle that combines properties
     * from both the local file and the classpath resource bundle.
     */
    @RequiredArgsConstructor
    private static class MergedResourceBundle extends ResourceBundle {
        private final @NonNull PropertyResourceBundle localBundle;
        private final @NonNull PropertyResourceBundle resourceBundle;

        @Override
        protected Object handleGetObject(@NonNull String key) {
            if (localBundle.containsKey(key)) {
                return localBundle.getString(key);
            }

            return resourceBundle.getObject(key);
        }

        @Override
        public @NonNull Enumeration<String> getKeys() {
            final Set<String> combinedKeys = new HashSet<>();
            final Enumeration<String> resourceKeys = resourceBundle.getKeys();
            resourceKeys.asIterator().forEachRemaining(combinedKeys::add);

            final Enumeration<String> localKeys = localBundle.getKeys();
            localKeys.asIterator().forEachRemaining(combinedKeys::add);

            return Collections.enumeration(combinedKeys);
        }
    }
}
