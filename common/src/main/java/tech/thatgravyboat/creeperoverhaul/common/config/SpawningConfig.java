package tech.thatgravyboat.creeperoverhaul.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Category;
import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;

@Category("spawning")
public final class SpawningConfig {

    @ConfigEntry(id = "allowSpawning", translation = "Allow Spawning")
    @Comment("Change the Creeper Overhaul creepers to spawn or not.")
    public static boolean allowSpawning = true;

    @ConfigEntry(id = "allowJungleCreeperSpawning", translation = "Allow Jungle Creeper Spawning")
    @Comment("Change the Jungle Creeper to spawn or not.")
    public static boolean allowJungleCreeperSpawning = true;

    @ConfigEntry(id = "allowBambooCreeperSpawning", translation = "Allow Bamboo Creeper Spawning")
    @Comment("Change the Bamboo Creeper to spawn or not.")
    public static boolean allowBambooCreeperSpawning = true;

    @ConfigEntry(id = "allowDesertCreeperSpawning", translation = "Allow Desert Creeper Spawning")
    @Comment("Change the Desert Creeper to spawn or not.")
    public static boolean allowDesertCreeperSpawning = true;

    @ConfigEntry(id = "allowBadlandsCreeperSpawning", translation = "Allow Badlands Creeper Spawning")
    @Comment("Change the Badlands Creeper to spawn or not.")
    public static boolean allowBadlandsCreeperSpawning = true;

    @ConfigEntry(id = "allowHillsCreeperSpawning", translation = "Allow Hills Creeper Spawning")
    @Comment("Change the Hills Creeper to spawn or not.")
    public static boolean allowHillsCreeperSpawning = true;

    @ConfigEntry(id = "allowSavannahCreeperSpawning", translation = "Allow Savannah Creeper Spawning")
    @Comment("Change the Savannah Creeper to spawn or not.")
    public static boolean allowSavannahCreeperSpawning = true;

    @ConfigEntry(id = "allowMushroomCreeperSpawning", translation = "Allow Mushroom Creeper Spawning")
    @Comment("Change the Mushroom Creeper to spawn or not.")
    public static boolean allowMushroomCreeperSpawning = true;

    @ConfigEntry(id = "allowSwampCreeperSpawning", translation = "Allow Swamp Creeper Spawning")
    @Comment("Change the Swamp Creeper to spawn or not.")
    public static boolean allowSwampCreeperSpawning = true;

    @ConfigEntry(id = "allowDripstoneCreeperSpawning", translation = "Allow Dripstone Creeper Spawning")
    @Comment("Change the Dripstone Creeper to spawn or not.")
    public static boolean allowDripstoneCreeperSpawning = true;

    @ConfigEntry(id = "allowCaveCreeperSpawning", translation = "Allow Cave Creeper Spawning")
    @Comment("Change the Cave Creeper to spawn or not.")
    public static boolean allowCaveCreeperSpawning = true;

    @ConfigEntry(id = "allowDarkOakCreeperSpawning", translation = "Allow Dark Oak Creeper Spawning")
    @Comment("Change the Dark Oak Creeper to spawn or not.")
    public static boolean allowDarkOakCreeperSpawning = true;

    @ConfigEntry(id = "allowSpruceCreeperSpawning", translation = "Allow Spruce Creeper Spawning")
    @Comment("Change the Spruce Creeper to spawn or not.")
    public static boolean allowSpruceCreeperSpawning = true;

    @ConfigEntry(id = "allowBeachCreeperSpawning", translation = "Allow Beach Creeper Spawning")
    @Comment("Change the Beach Creeper to spawn or not.")
    public static boolean allowBeachCreeperSpawning = true;

    @ConfigEntry(id = "allowSnowyCreeperSpawning", translation = "Allow Snowy Creeper Spawning")
    @Comment("Change the Snowy Creeper to spawn or not.")
    public static boolean allowSnowyCreeperSpawning = true;

    @ConfigEntry(id = "allowOceanCreeperSpawning", translation = "Allow Ocean Creeper Spawning")
    @Comment("Change the Ocean Creeper to spawn or not.")
    public static boolean allowOceanCreeperSpawning = true;

    @ConfigEntry(id = "allowBirchCreeperSpawning", translation = "Allow Birch Creeper Spawning")
    @Comment("Change the Birch Creeper to spawn or not.")
    public static boolean allowBirchCreeperSpawning = true;

}
