package tech.thatgravyboat.creeperoverhaul.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.*;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;

@Config(
    "crepperoverhaul-client"
)
@ConfigInfo(
    icon = "creeper",
    title = "Creeper Overhaul Client",
    description = "Client side configuration for Creeper Overhaul"
)
@ConfigOption.Hidden
public final class ClientConfig {

    @ConfigEntry(
        type = EntryType.BOOLEAN,
        id = "replaceDefaultCreeper",
        translation = "Replace Default Creeper"
    )
    @Comment("Change the Vanilla Creeper to a new and improved texture with better animations.")
    public static boolean replaceDefaultCreeper = true;


}
