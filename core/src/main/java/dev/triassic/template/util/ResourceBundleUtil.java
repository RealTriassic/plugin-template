package dev.triassic.template.util;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ResourceBundleUtil {

    /**
     * Loads a resource bundle from the given file path. This method can load properties
     * files from the filesystem or fallback to loading from the classpath.
     *
     * @param filePath the path to the properties file on the filesystem
     * @param fileName the name of the file to be used when looking up in classpath
     * @param locale   the locale for the resource bundle, if needed
     * @return the resource bundle loaded from the file or classpath
     */
    public static ResourceBundle loadResourceBundle(
            final Path filePath,
            final String fileName,
            final Locale locale
    ) {
        final ResourceBundle bundle = loadFromFile(filePath, fileName);
        if (bundle != null)
            return bundle;

        return loadFromClasspath(fileName, locale);
    }

    private static ResourceBundle loadFromFile(
            final @NonNull Path path,
            final @NonNull String fileName
    ) {
        if (Files.exists(path)) {
            try (
                    final InputStream inputStream = Files.newInputStream(path)
            ) {
                final Properties properties = new Properties();
                properties.load(inputStream);

                return new PropertyResourceBundle(new StringReader(properties.toString()));
            } catch (IOException e) {
                // TODO: Handle exception.
            }
        }

        return null;
    }

    private static ResourceBundle loadFromClasspath(
            final @NonNull String fileName,
            final @NonNull Locale locale
    ) {
        try {
            return ResourceBundle.getBundle(fileName, locale);
        } catch (MissingResourceException e) {
            return ResourceBundle.getBundle(fileName, Locale.ENGLISH);
        }
    }
}
