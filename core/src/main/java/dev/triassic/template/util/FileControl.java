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
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * An implementation of {@link ResourceBundle.Control} to attempt to load
 * resource bundles with the UTF-8 charset from both the classpath and
 * local files using {@link MergedResourceBundle}, prioritizing local files.
 */
@RequiredArgsConstructor
public class FileControl extends ResourceBundle.Control {

    private @NonNull Path localPath;

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
                resourceBundle = new PropertyResourceBundle(
                    new InputStreamReader(resourceStream, StandardCharsets.UTF_8));
            }

            localPath = localPath.resolve(resourceName);
            if (Files.exists(localPath)) {
                try (final InputStream localStream = Files.newInputStream(localPath)) {
                    final PropertyResourceBundle localBundle =
                        new PropertyResourceBundle(
                            new InputStreamReader(localStream, StandardCharsets.UTF_8));
                    if (resourceBundle != null) {
                        return new MergedResourceBundle(localBundle, resourceBundle);
                    }

                    return localBundle;
                }
            }

            return resourceBundle;
        }
    }

    /**
     * A resource bundle that combines properties from both
     * the locally stored file and the classpath resource bundle.
     */
    @RequiredArgsConstructor
    private static class MergedResourceBundle extends ResourceBundle {
        private final @NonNull PropertyResourceBundle localBundle;
        private final @NonNull PropertyResourceBundle resourceBundle;

        @Override
        protected Object handleGetObject(final @NonNull String key) {
            return localBundle.containsKey(key)
                ? localBundle.getString(key)
                : resourceBundle.getString(key);
        }

        @Override
        public @NonNull Enumeration<String> getKeys() {
            return Collections.enumeration(
                Stream.concat(
                        Collections.list(resourceBundle.getKeys()).stream(),
                        Collections.list(localBundle.getKeys()).stream()
                    )
                    .collect(Collectors.toSet())
            );
        }
    }
}
