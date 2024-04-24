package tech.thatgravyboat.creeperoverhaul;

import com.teamresourceful.resourcefulconfig.common.config.Configurator;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import software.bernie.geckolib.GeckoLib;
import tech.thatgravyboat.creeperoverhaul.common.config.CreepersConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModBlocks;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModEntities;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModItems;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSounds;
import tech.thatgravyboat.creeperoverhaul.common.utils.Events;

import java.util.Map;

public class Creepers {

    public static final Configurator CONFIGURATOR = new Configurator();

    public static final String MODID = "creeperoverhaul";
    public static final Events EVENT = Events.getCurrentEvent();

    public static void init() {
        CONFIGURATOR.registerConfig(CreepersConfig.class);
        GeckoLib.initialize();
        ModBlocks.BLOCKS.init();
        ModEntities.ENTITIES.init();
        ModItems.ITEMS.init();
        ModSounds.SOUNDS.init();
    }

    public static void registerAttributes(Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes) {
        attributes.put(ModEntities.JUNGLE_CREEPER.get(), CreeperTypes.JUNGLE.attributes());
        attributes.put(ModEntities.BAMBOO_CREEPER.get(), CreeperTypes.BAMBOO.attributes());
        attributes.put(ModEntities.DESERT_CREEPER.get(), CreeperTypes.DESERT.attributes());
        attributes.put(ModEntities.BADLANDS_CREEPER.get(), CreeperTypes.BADLANDS.attributes());
        attributes.put(ModEntities.HILLS_CREEPER.get(), CreeperTypes.HILLS.attributes());
        attributes.put(ModEntities.SAVANNAH_CREEPER.get(), CreeperTypes.SAVANNAH.attributes());
        attributes.put(ModEntities.MUSHROOM_CREEPER.get(), CreeperTypes.MUSHROOM.attributes());
        attributes.put(ModEntities.SWAMP_CREEPER.get(), CreeperTypes.SWAMP.attributes());
        attributes.put(ModEntities.DRIPSTONE_CREEPER.get(), CreeperTypes.DRIPSTONE.attributes());
        attributes.put(ModEntities.CAVE_CREEPER.get(), CreeperTypes.CAVE.attributes());
        attributes.put(ModEntities.DARK_OAK_CREEPER.get(), CreeperTypes.DARK_OAK.attributes());
        attributes.put(ModEntities.SPRUCE_CREEPER.get(), CreeperTypes.SPRUCE.attributes());
        attributes.put(ModEntities.BEACH_CREEPER.get(), CreeperTypes.BEACH.attributes());
        attributes.put(ModEntities.SNOWY_CREEPER.get(), CreeperTypes.SNOWY.attributes());
        attributes.put(ModEntities.OCEAN_CREEPER.get(), CreeperTypes.OCEAN.attributes());
    }
}
