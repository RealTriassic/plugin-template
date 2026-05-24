/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.i18n;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.minimessage.translation.MiniMessageTranslationStore;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationStore;

/**
 * A manager for extracting, loading, and registering the plugin's translations.
 *
 * <p>Provides methods to load, reload, and access the translation store data,
 * prioritizing external file overrides over internal jar resources.</p>
 */
public record TranslationManager(
    Path langDirectory,
    AtomicReference<TranslationStore.StringBased<String>> internalStore
) {

    private static final Key REGISTRY_KEY = Key.key("template", "translations");
    private static final String BUNDLE_BASE_NAME = "messages";

    /**
     * Loads the translation bundles from the path and
     * creates a new {@link TranslationManager} instance.
     *
     * <p>If the translation directory or default files do not exist, they will be
     * created and extracted automatically from the jar resources.</p>
     *
     * @param path the path to the base data directory of the plugin
     * @return a {@link TranslationManager} instance containing the loaded store
     * @throws IOException if an error occurs while creating directories or copying files
     */
    public static TranslationManager load(Path path) throws IOException {
        Path langDirectory = path.resolve("lang");

        if (Files.notExists(langDirectory)) {
            Files.createDirectories(langDirectory);
        }

        Path defaultLangFile = langDirectory.resolve("messages_en_US.properties");
        if (Files.notExists(defaultLangFile)) {
            try (var in = TranslationManager.class.getResourceAsStream(
                "/lang/messages_en_US.properties")) {
                if (in != null) {
                    Files.copy(in, defaultLangFile);
                }
            }
        }

        TranslationStore.StringBased<String> store;
        try {
            store = createStore(langDirectory);
        } catch (Exception e) {
            throw new IOException("Failed to initialize translation store", e);
        }

        GlobalTranslator.translator().addSource(store);

        return new TranslationManager(langDirectory, new AtomicReference<>(store));
    }

    private static TranslationStore.StringBased<String> createStore(Path langDirectory)
        throws Exception {
        var store = MiniMessageTranslationStore.create(REGISTRY_KEY);
        store.defaultLocale(Locale.US);

        URL[] urls = {langDirectory.toUri().toURL()};

        var classLoader = new URLClassLoader(urls, TranslationManager.class.getClassLoader());
        ResourceBundle.clearCache(classLoader);
        ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_BASE_NAME, Locale.US, classLoader);

        store.registerAll(Locale.US, bundle, true);

        return store;
    }

    /**
     * Synchronously reloads the translations from disk.
     *
     * <p>The current translation store is updated with the newly loaded data and
     * re-registered with Adventure's global translator pipeline.</p>
     */
    public void reload() {
        try {
            var oldStore = this.internalStore.get();
            var newStore = createStore(this.langDirectory);

            GlobalTranslator.translator().addSource(newStore);
            if (oldStore != null) {
                GlobalTranslator.translator().removeSource(oldStore);
            }

            this.internalStore.set(newStore);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reload translations synchronously", e);
        }
    }

    /**
     * Gets the current translation store instance.
     *
     * @return the current translation store
     */
    public TranslationStore.StringBased<String> get() {
        return this.internalStore.get();
    }
}
