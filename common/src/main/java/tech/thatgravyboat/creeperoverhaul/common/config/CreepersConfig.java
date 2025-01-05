package tech.thatgravyboat.creeperoverhaul.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;

@Config(
        value = "creeperoverhaul",
        categories = {SpawningConfig.class}
)
@ConfigInfo(
        icon = "creeper",
        title = "Creeper Overhaul",
        description = "Biome specific creepers",
        links = {
                @ConfigInfo.Link(
                        value = "https://modrinth.com/mod/creeper-overhaul",
                        icon = "modrinth",
                        text = "Modrinth"
                ),
                @ConfigInfo.Link(
                        value = "https://curseforge.com/minecraft/mc-mods/creeper-overhaul",
                        icon = "curseforge",
                        text = "Curseforge"
                ),
                @ConfigInfo.Link(
                        value = "https://github.com/bonsaistudi0s/Creeper-Overhaul",
                        icon = "github",
                        text = "Github"
                )
        }
)
@ConfigInfo.Color("#7BB252")
public final class CreepersConfig {

    @ConfigEntry(id = "destroyBlocks", translation = "Destroy Blocks")
    @Comment("Changes the Creeper Overhaul creepers to destroy blocks or not.")
    public static boolean destroyBlocks = true;
}
