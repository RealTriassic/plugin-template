/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.i18n;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

/**
 * Centralized enumeration of all translatable messages within the plugin.
 */
@RequiredArgsConstructor
public enum TranslationKey implements ComponentLike {

    COMMAND_RELOAD_SUCCESS("command.reload.success"),
    COMMAND_RELOAD_FAIL("command.reload.fail");

    private final String key;

    /**
     * Builds a translatable component replacing placeholders with the provided arguments.
     *
     * @param args the components to replace placeholders (e.g., {0}, {1})
     * @return a constructed translatable component
     */
    public Component withArgs(ComponentLike... args) {
        return Component.translatable(key, args);
    }

    @Override
    public @NotNull Component asComponent() {
        return Component.translatable(key);
    }
}