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
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Utility class for working with resources in JAR files and on the file system.
 */
@UtilityClass
public final class ResourceUtil {

    /**
     * Lists all resources (files) in a given directory, either in a JAR or on the file system.
     * The directory is assumed to be relative to the classpath.
     *
     * @param directory the directory to search within (e.g., "config/lang").
     * @return a set of resource paths as strings.
     * @throws IOException if an error occurs while accessing the resources.
     */
    @NonNull
    public static Set<Path> listResources(
        final @NonNull String directory
    ) throws IOException, URISyntaxException {
        final ClassLoader loader = ResourceUtil.class.getClassLoader();
        final Enumeration<URL> urls = loader.getResources(directory);

        while (urls.hasMoreElements()) {
            final URL url = urls.nextElement();
            final String protocol = url.getProtocol();

            if ("jar".equals(protocol)) {
                return listResourcesInJar(url.toURI(), directory);
            } else if ("file".equals(protocol)) {
                return listResourcesInFileSystem(url.toURI());
            }
        }

        return Set.of();
    }

    /**
     * Lists all resources in a JAR file.
     *
     * @param jarUri the URI of the JAR file
     * @param directory the directory inside the JAR to search in
     * @return a set of resource paths inside the JAR file
     * @throws IOException if an error occurs while reading the JAR file
     */
    @NonNull
    private Set<Path> listResourcesInJar(
        final @NonNull URI jarUri,
        final @NonNull String directory
    ) throws IOException {
        final Set<Path> resourcePaths = new HashSet<>();

        final String jarFileUri = jarUri.toString().split("!")[0];
        final URI jarFilePath = URI.create(jarFileUri + "!/");

        try (final FileSystem fs = FileSystems.newFileSystem(jarFilePath, Map.of())) {
            final Path path = fs.getPath(directory);
            try (Stream<Path> stream = Files.walk(path)) {
                stream.filter(Files::isRegularFile)
                    .forEach(file -> resourcePaths.add(path.relativize(file)));
            }
        }

        return resourcePaths;
    }

    /**
     * Lists all resources in a file system directory.
     *
     * @param dirUri the URI of the directory
     * @return a set of resource paths in the file system directory
     * @throws IOException if an error occurs while reading the file system
     */
    @NonNull
    private Set<Path> listResourcesInFileSystem(
        final @NonNull URI dirUri
    ) throws IOException {
        final Set<Path> resourcePaths = new HashSet<>();

        final Path path = Paths.get(dirUri);
        if (Files.exists(path) && Files.isDirectory(path)) {
            try (Stream<Path> stream = Files.walk(path)) {
                stream.filter(Files::isRegularFile)
                    .forEach(file -> resourcePaths.add(path.relativize(file)));
            }
        }

        return resourcePaths;
    }
}

