/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.command;

import net.kyori.adventure.audience.Audience;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a {@link TemplateCommand} executor.
 */
public interface Commander extends Audience {

    /**
     * Checks if this command executor has the given permission.
     *
     * @param permission The permission to check for
     * @return {@code true} if this executor has the given permission
     */
    boolean hasPermission(final @NonNull String permission);
}
