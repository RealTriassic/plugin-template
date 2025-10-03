/*
 * SPDX-License-Identifier: CC0-1.0
 *
 * Dedicated to the public domain under CC0 1.0 Universal.
 *
 * You can obtain a full copy of the license at:
 *     https://creativecommons.org/publicdomain/zero/1.0/
 */

package dev.triassic.template.annotation;

import dev.triassic.template.util.PlatformType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Excludes a command class or configuration field from certain platforms.
 *
 * <p>Use this to prevent commands or config nodes from being registered or loaded
 * on platforms where they are not supported. Elements without this annotation
 * are included on all platforms by default.</p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface ExcludePlatform {

    /**
     * The platforms on which the annotated element should be excluded.
     *
     * @return an array of {@link PlatformType} to exclude
     */
    PlatformType[] value();
}
