package dev.triassic.template.command;

import net.kyori.adventure.audience.Audience;

public interface CommandSource extends Audience {

    /**
     * Checks if this command source has the given permission.
     *
     * @param permission The permission node to check for
     * @return true if this command source has a permission
     */
    boolean hasPermission(String permission);
}
