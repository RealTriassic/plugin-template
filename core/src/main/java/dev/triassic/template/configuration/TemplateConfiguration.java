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
import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

/**
 * Represents the plugin's base configuration file.
 */
@Getter
@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public class TemplateConfiguration {

    @Comment("This string should not appear on Velocity and Bungeecord platforms.")
    @ExcludePlatform({PlatformType.BUNGEECORD, PlatformType.VELOCITY})
    private String exampleString = "This is an example string!";

    @Comment("Used internally, do not change.")
    private int configVersion = 1;
}