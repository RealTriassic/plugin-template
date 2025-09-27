/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents a platform type with a display name and whether it is a proxy.
 */
@Getter
@RequiredArgsConstructor
public enum PlatformType {

    BUKKIT("Bukkit", false),
    BUNGEECORD("Bungeecord", true),
    FABRIC("Fabric", false),
    NEOFORGE("NeoForge", false),
    PAPER("Paper", false),
    VELOCITY("Velocity", true);

    private final String displayName;
    private final boolean isProxy;
}