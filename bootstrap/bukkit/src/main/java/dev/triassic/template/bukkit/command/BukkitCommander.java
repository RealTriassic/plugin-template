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

package dev.triassic.template.bukkit.command;

import dev.triassic.template.bukkit.BukkitTemplatePlugin;
import dev.triassic.template.command.Commander;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.framework.qual.DefaultQualifier;

/**
 * Represents a Bukkit-specific {@link Commander}.
 *
 * <p>Slightly borrowed from <a href="https://github.com/Hexaoxide/Carbon">Carbon's</a> implementation.</p>
 */
@DefaultQualifier(NonNull.class)
public interface BukkitCommander extends Commander, ForwardingAudience.Single {

    /**
     * Create a new {@link BukkitCommander} from a {@link CommandSender}.
     *
     * @param sender    the {@link CommandSender}
     * @return a new {@link BukkitCommander}
     */
    static BukkitCommander from(final CommandSender sender) {
        return new BukkitCommanderImpl(sender);
    }

    /**
     * Gets the underlying {@link CommandSender}.
     *
     * @return the {@link CommandSender}
     */
    CommandSender sender();

    /**
     * A record implementation of {@link BukkitCommander}.
     */
    record BukkitCommanderImpl(CommandSender sender)
        implements BukkitCommander {

        @Override
        public Audience audience() {
            return BukkitTemplatePlugin.getAdventure().sender(sender);
        }

        @Override
        public boolean hasPermission(final String permission) {
            return sender.hasPermission(permission);
        }
    }
}
