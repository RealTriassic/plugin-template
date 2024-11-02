package dev.triassic.template.common.configuration;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Setting;

@Getter
@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public class Configuration {

    @Setting("config-version")
    @Comment("Used internally, do not change.")
    private int configVersion = 1;
}