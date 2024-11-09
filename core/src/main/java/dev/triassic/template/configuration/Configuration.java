package dev.triassic.template.configuration;

import lombok.Getter;
import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Comment;

@Getter
@ConfigSerializable
@SuppressWarnings("FieldMayBeFinal")
public class Configuration {

    @Comment("The default locale used in messages.")
    private String defaultLocale = "en-US";

    @Comment("Used internally, do not change.")
    private int configVersion = 1;
}