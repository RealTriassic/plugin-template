package dev.triassic.template.configuration;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@Getter
@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public final class Configuration {

    @Setting("config-version")
    @Comment("Used internally, do not change.")
    private int configVersion = 1;
}