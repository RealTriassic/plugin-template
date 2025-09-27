/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.configuration;

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

    /**
     * The version of the configuration file.
     */
    @Comment("Used internally, do not change.")
    private int configVersion = 1;
}