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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Utility class for checking updates from a GitHub repository.
 */
@DefaultQualifier(NonNull.class)
public final class UpdateChecker {

    private static final String USER_AGENT = "Mozilla/5.0";
    private static final String API_URL_TEMPLATE = "https://api.github.com/repos/%s/releases/latest";

    /**
     * Checks for updates asynchronously.
     *
     * @param repository     the GitHub repository in the format "owner/repo"
     * @param currentVersion the current version of the application
     * @return a {@link CompletableFuture} with the {@link UpdateResult}
     */
    public static CompletableFuture<UpdateResult> checkForUpdates(
        final String repository, final String currentVersion) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                final String apiUrl = String.format(API_URL_TEMPLATE, repository);
                final HttpURLConnection connection = createConnection(apiUrl);

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String latestVersion = extractLatestVersion(readResponse(connection));
                    return compareVersions(currentVersion, latestVersion);
                } else {
                    return UpdateResult.FAILED;
                }
            } catch (Exception e) {
                return UpdateResult.FAILED;
            }
        });
    }

    /**
     * Creates and configures an HTTP connection to the specified URL.
     *
     * @param url the target URL
     * @return the configured {@link HttpURLConnection}
     * @throws Exception if the connection could not be established
     */
    private static HttpURLConnection createConnection(final String url) throws Exception {
        final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestProperty("User-Agent", USER_AGENT);
        connection.setRequestMethod("GET");
        return connection;
    }

    /**
     * Reads the response from an {@link HttpURLConnection}.
     *
     * @param connection the open connection
     * @return the response body as a {@link String}
     * @throws Exception if an error occurs while reading the response
     */
    private static String readResponse(HttpURLConnection connection) throws Exception {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
            new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }

    /**
     * Extracts the latest version from the JSON response.
     *
     * @param response the JSON response
     * @return the latest version as a {@link String}
     * @throws IllegalStateException if the response format is invalid
     */
    private static String extractLatestVersion(final String response) {
        int startIndex = response.indexOf("\"tag_name\":\"") + 12;
        int endIndex = response.indexOf("\"", startIndex);
        if (startIndex > 11 && endIndex > startIndex) {
            return response.substring(startIndex, endIndex);
        } else {
            throw new IllegalStateException("Invalid response format");
        }
    }

    /**
     * Compares the current version with the latest version.
     *
     * @param currentVersion the current version of the application
     * @param latestVersion  the latest version from the repository
     * @return the corresponding {@link UpdateResult}
     */
    private static UpdateResult compareVersions(
        final String currentVersion, final String latestVersion) {
        return latestVersion.equalsIgnoreCase(currentVersion)
            ? UpdateResult.UP_TO_DATE
            : UpdateResult.OUTDATED;
    }
}
