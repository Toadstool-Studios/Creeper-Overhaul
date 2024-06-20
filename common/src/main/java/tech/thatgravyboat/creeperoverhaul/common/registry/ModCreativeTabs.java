package tech.thatgravyboat.creeperoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.item.tabs.ResourcefulCreativeModeTab;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Supplier;

public class ModCreativeTabs {

    public static final ResourcefulRegistry<CreativeModeTab> CREATIVE_TABS = ResourcefulRegistries.create(BuiltInRegistries.CREATIVE_MODE_TAB, Creepers.MODID);

    public static final Supplier<CreativeModeTab> CREEPER_OVERHAUL = CREATIVE_TABS.register("item_group", () ->
            new ResourcefulCreativeModeTab(new ResourceLocation(Creepers.MODID, "item_group"))
                    .setItemIcon(() -> Items.CREEPER_HEAD)
                    .addRegistry(ModItems.ITEMS)
                    .build());
}
