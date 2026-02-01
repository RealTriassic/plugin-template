/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.configuration;

import dev.triassic.template.annotation.ExcludePlatform;
import dev.triassic.template.util.PlatformType;
import org.spongepowered.configurate.interfaces.meta.defaults.DefaultNumeric;
import org.spongepowered.configurate.interfaces.meta.defaults.DefaultString;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

/**
 * Represents the plugin's base configuration file.
 */
@ConfigSerializable
public interface TemplateConfiguration {

    /**
     * An example string that showcases the usage of {@link ExcludePlatform}.
     *
     * <p>Take note of how the {@link ExcludePlatform} annotation goes after
     * the {@link Comment} annotation, it is important to do it in this order,
     * otherwise the node will be recreated empty to add the comment.</p>
     */
    @Comment("This string should not appear on Velocity and Bungeecord platforms.")
    @DefaultString("This is an example string!")
    @ExcludePlatform({PlatformType.BUNGEECORD, PlatformType.VELOCITY})
    String exampleString();

    /**
     * The version of the configuration file.
     */
    @Comment("Used internally, do not change.")
    @DefaultNumeric(1)
    int configVersion();
}