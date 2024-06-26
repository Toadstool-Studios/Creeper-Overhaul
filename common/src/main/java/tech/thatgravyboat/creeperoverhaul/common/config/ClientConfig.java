package tech.thatgravyboat.creeperoverhaul.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.*;
import com.teamresourceful.resourcefulconfig.api.types.entries.Observable;
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
    @Comment(
        """
        Change the Vanilla Creeper to a new and improved texture with better animations.
        §cNote: Restart required to see changes.
        """
    )
    public static boolean replaceDefaultCreeper = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "showCosmetic",
            translation = "Show Cosmetic"
    )
    @Comment(
            """
            Shows your cosmetic on your player for others.
            §eNote: Only available for players with the cosmetic.
            """
    )
    public static Observable<Boolean> showCosmetic = Observable.of(true);

}
