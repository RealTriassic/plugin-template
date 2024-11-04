package dev.triassic.template.command;

import net.kyori.adventure.audience.Audience;

public interface Commander extends Audience {

    boolean hasPermission(String permission);
}
