package dev.triassic.template.util;

import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

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
     * Custom {@link ResourceBundle.Control} implementation to load resources from both the classpath and local files.
     */
    @RequiredArgsConstructor
    private static class FileControl extends ResourceBundle.Control {
        private @NonNull Path path;

        @Override
        public @Nullable ResourceBundle newBundle(String baseName, Locale locale, String format,
                                                  ClassLoader loader, boolean reload)
                throws IllegalAccessException, InstantiationException, IOException {
            if (!"java.properties".equals(format))
                return super.newBundle(baseName, locale, format, loader, reload);

            final String bundleName = toBundleName(baseName, locale);
            final String resourceName = toResourceName(bundleName, "properties");

            PropertyResourceBundle resourceBundle = null;
            try (final InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
                if (resourceStream != null)
                    resourceBundle = new PropertyResourceBundle(resourceStream);

                path = path.resolve(resourceName);
                if (Files.exists(path)) {
                    try (final InputStream localStream = Files.newInputStream(path)) {
                        final PropertyResourceBundle localBundle = new PropertyResourceBundle(localStream);
                        if (resourceBundle != null)
                            return new MergedResourceBundle(localBundle, resourceBundle);

                        return localBundle;
                    }
                }

                return resourceBundle;
            }
        }
    }

    /**
     * A merged resource bundle that combines properties from both the local file and the classpath resource bundle.
     */
    @RequiredArgsConstructor
    private static class MergedResourceBundle extends ResourceBundle {
        private final @NonNull PropertyResourceBundle localBundle;
        private final @NonNull PropertyResourceBundle resourceBundle;

        @Override
        protected Object handleGetObject(@NonNull String key) {
            if (localBundle.containsKey(key))
                return localBundle.getString(key);

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
