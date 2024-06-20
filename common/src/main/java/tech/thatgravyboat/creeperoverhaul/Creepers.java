package tech.thatgravyboat.creeperoverhaul;

import com.teamresourceful.resourcefulconfig.api.loader.Configurator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import tech.thatgravyboat.creeperoverhaul.common.config.CreepersConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperAttributeBuilder;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;
import tech.thatgravyboat.creeperoverhaul.common.registry.*;
import tech.thatgravyboat.creeperoverhaul.common.utils.Events;

import java.util.Map;

public class Creepers {

    public static final String MODID = "creeperoverhaul";
    public static final Events EVENT = Events.getCurrentEvent();
    public static final Configurator CONFIGURATOR = new Configurator(MODID);

    public static void init() {
        CONFIGURATOR.register(CreepersConfig.class);
        ModBlocks.BLOCKS.init();
        ModEntities.ENTITIES.init();
        ModCreativeTabs.CREATIVE_TABS.init();
        ModItems.ITEMS.init();
        ModSounds.SOUNDS.init();
    }

    public static void registerAttributes(Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes) {
        attributes.put(ModEntities.JUNGLE_CREEPER.get(), build(CreeperTypes.JUNGLE));
        attributes.put(ModEntities.BAMBOO_CREEPER.get(), build(CreeperTypes.BAMBOO));
        attributes.put(ModEntities.DESERT_CREEPER.get(), build(CreeperTypes.DESERT));
        attributes.put(ModEntities.BADLANDS_CREEPER.get(), build(CreeperTypes.BADLANDS));
        attributes.put(ModEntities.HILLS_CREEPER.get(), build(CreeperTypes.HILLS));
        attributes.put(ModEntities.SAVANNAH_CREEPER.get(), build(CreeperTypes.SAVANNAH));
        attributes.put(ModEntities.MUSHROOM_CREEPER.get(), build(CreeperTypes.MUSHROOM));
        attributes.put(ModEntities.SWAMP_CREEPER.get(), build(CreeperTypes.SWAMP));
        attributes.put(ModEntities.DRIPSTONE_CREEPER.get(), build(CreeperTypes.DRIPSTONE));
        attributes.put(ModEntities.CAVE_CREEPER.get(), build(CreeperTypes.CAVE));
        attributes.put(ModEntities.DARK_OAK_CREEPER.get(), build(CreeperTypes.DARK_OAK));
        attributes.put(ModEntities.SPRUCE_CREEPER.get(), build(CreeperTypes.SPRUCE));
        attributes.put(ModEntities.BEACH_CREEPER.get(), build(CreeperTypes.BEACH));
        attributes.put(ModEntities.SNOWY_CREEPER.get(), build(CreeperTypes.SNOWY));
        attributes.put(ModEntities.OCEAN_CREEPER.get(), build(CreeperTypes.OCEAN));
        attributes.put(ModEntities.BIRCH_CREEPER.get(), build(CreeperTypes.BIRCH));
    }

    private static AttributeSupplier.Builder build(CreeperType type) {
        CreeperAttributeBuilder builder = new CreeperAttributeBuilder();
        type.attributes().accept(builder);
        return builder.build();
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
