package dev.triassic.template.util;

public record PlatformType(String displayName, boolean isProxy) {

    public static final PlatformType BUKKIT = new PlatformType("Bukkit", false);
    public static final PlatformType BUNGEECORD = new PlatformType("Bungeecord", true);
    public static final PlatformType VELOCITY = new PlatformType("Velocity", true);
    public static final PlatformType GEYSER = new PlatformType("Geyser", true);
}