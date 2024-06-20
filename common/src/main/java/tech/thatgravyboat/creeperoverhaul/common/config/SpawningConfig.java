package tech.thatgravyboat.creeperoverhaul.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Category;
import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;

@Category("spawning")
public final class SpawningConfig {

    @ConfigEntry(
        type = EntryType.BOOLEAN,
        id = "allowSpawning",
        translation = "Allow Spawning"
    )
    @Comment("Change the Creeper Overhaul creepers to spawn or not.")
    public static boolean allowSpawning = true;

    @ConfigEntry(
        type = EntryType.BOOLEAN,
        id = "allowJungleCreeperSpawning",
        translation = "Allow Jungle Creeper Spawning"
    )
    @Comment("Change the Jungle Creeper to spawn or not.")
    public static boolean allowJungleCreeperSpawning = true;

    @ConfigEntry(
        type = EntryType.BOOLEAN,
        id = "allowBambooCreeperSpawning",
        translation = "Allow Bamboo Creeper Spawning"
    )
    @Comment("Change the Bamboo Creeper to spawn or not.")
    public static boolean allowBambooCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowDesertCreeperSpawning",
            translation = "Allow Desert Creeper Spawning"
    )
    @Comment("Change the Desert Creeper to spawn or not.")
    public static boolean allowDesertCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowBadlandsCreeperSpawning",
            translation = "Allow Badlands Creeper Spawning"
    )
    @Comment("Change the Badlands Creeper to spawn or not.")
    public static boolean allowBadlandsCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowHillsCreeperSpawning",
            translation = "Allow Hills Creeper Spawning"
    )
    @Comment("Change the Hills Creeper to spawn or not.")
    public static boolean allowHillsCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowSavannahCreeperSpawning",
            translation = "Allow Savannah Creeper Spawning"
    )
    @Comment("Change the Savannah Creeper to spawn or not.")
    public static boolean allowSavannahCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowMushroomCreeperSpawning",
            translation = "Allow Mushroom Creeper Spawning"
    )
    @Comment("Change the Mushroom Creeper to spawn or not.")
    public static boolean allowMushroomCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowSwampCreeperSpawning",
            translation = "Allow Swamp Creeper Spawning"
    )
    @Comment("Change the Swamp Creeper to spawn or not.")
    public static boolean allowSwampCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowDripstoneCreeperSpawning",
            translation = "Allow Dripstone Creeper Spawning"
    )
    @Comment("Change the Dripstone Creeper to spawn or not.")
    public static boolean allowDripstoneCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowCaveCreeperSpawning",
            translation = "Allow Cave Creeper Spawning"
    )
    @Comment("Change the Cave Creeper to spawn or not.")
    public static boolean allowCaveCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowDarkOakCreeperSpawning",
            translation = "Allow Dark Oak Creeper Spawning"
    )
    @Comment("Change the Dark Oak Creeper to spawn or not.")
    public static boolean allowDarkOakCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowSpruceCreeperSpawning",
            translation = "Allow Spruce Creeper Spawning"
    )
    @Comment("Change the Spruce Creeper to spawn or not.")
    public static boolean allowSpruceCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowBeachCreeperSpawning",
            translation = "Allow Beach Creeper Spawning"
    )
    @Comment("Change the Beach Creeper to spawn or not.")
    public static boolean allowBeachCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowSnowyCreeperSpawning",
            translation = "Allow Snowy Creeper Spawning"
    )
    @Comment("Change the Snowy Creeper to spawn or not.")
    public static boolean allowSnowyCreeperSpawning = true;

    @ConfigEntry(
            type = EntryType.BOOLEAN,
            id = "allowOceanCreeperSpawning",
            translation = "Allow Ocean Creeper Spawning"
    )
    @Comment("Change the Ocean Creeper to spawn or not.")
    public static boolean allowOceanCreeperSpawning = true;

}
