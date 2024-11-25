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

package dev.triassic.template.velocity.command;

import com.velocitypowered.api.command.CommandSource;
import dev.triassic.template.command.Commander;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Represents a Velocity-specific {@link Commander}.
 *
 * <p>Slightly borrowed from <a href="https://github.com/Hexaoxide/Carbon">Carbon's</a> implementation</p>
 */
@DefaultQualifier(NonNull.class)
public interface VelocityCommander extends Commander, ForwardingAudience.Single {

    /**
     * Create a new {@link VelocityCommander} from a {@link CommandSource}.
     *
     * @param sender the {@link CommandSource}
     * @return a new {@link VelocityCommander}
     */
    static VelocityCommander from(final CommandSource sender) {
        return new VelocityCommanderImpl(sender);
    }

    /**
     * Gets the underlying {@link CommandSource}.
     *
     * @return the {@link CommandSource}
     */
    CommandSource sender();

    /**
     * A record implementation of {@link VelocityCommander}.
     */
    record VelocityCommanderImpl(CommandSource sender) implements VelocityCommander {

        @Override
        public Audience audience() {
            return sender;
        }

        @Override
        public boolean hasPermission(final String permission) {
            return sender.hasPermission(permission);
        }
    }
}
