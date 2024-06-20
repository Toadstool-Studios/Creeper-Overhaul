package tech.thatgravyboat.creeperoverhaul.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModEntities;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSpawns;
import tech.thatgravyboat.creeperoverhaul.common.registry.fabric.FabricAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CreepersFabric implements ModInitializer {

    private static final TagKey<Biome> IS_CAVE = TagKey.create(Registries.BIOME, new ResourceLocation(Creepers.MODID, "is_cave"));
    private static final TagKey<Biome> IS_DARKFOREST = TagKey.create(Registries.BIOME, new ResourceLocation(Creepers.MODID, "is_darkforest"));
    private static final TagKey<Biome> IS_MUSHROOM = TagKey.create(Registries.BIOME, new ResourceLocation(Creepers.MODID, "is_mushroom"));

    @Override
    public void onInitialize() {
        Creepers.init();
        FabricAttributes.register();
        Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes = new HashMap<>();
        Creepers.registerAttributes(attributes);
        attributes.forEach(FabricDefaultAttributeRegistry::register);
        addCreepers();
        removeCreepers();
        ModSpawns.addSpawnRules();

        ServerLifecycleEvents.SERVER_STARTING.register(event -> PluginLoader.load());
    }

    public void addCreepers() {
        addCreeper(tag(BiomeTags.IS_BEACH), ModEntities.BEACH_CREEPER);
        addCreeper(tag(BiomeTags.IS_BEACH), ModEntities.CAVE_CREEPER, 50);

        addCreeper(isSnowing(), ModEntities.SNOWY_CREEPER);
        addCreeper(isSnowing(), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(BiomeTags.HAS_DESERT_PYRAMID), ModEntities.DESERT_CREEPER);
        addCreeper(tag(BiomeTags.HAS_DESERT_PYRAMID), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(BiomeTags.IS_HILL), ModEntities.HILLS_CREEPER);
        addCreeper(tag(BiomeTags.IS_HILL), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(BiomeTags.IS_SAVANNA), ModEntities.SAVANNAH_CREEPER);
        addCreeper(tag(BiomeTags.IS_SAVANNA), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(BiomeTags.HAS_MINESHAFT_MESA), ModEntities.BADLANDS_CREEPER);
        addCreeper(tag(BiomeTags.HAS_MINESHAFT_MESA), ModEntities.CAVE_CREEPER, 50);

        BiomeModifications.addSpawn(tag(IS_MUSHROOM), ModEntities.MUSHROOM_CREEPER.get().getCategory(), ModEntities.MUSHROOM_CREEPER.get(), 10, 1, 2);

        addCreeper(tag(BiomeTags.IS_TAIGA), ModEntities.SPRUCE_CREEPER);
        addCreeper(tag(BiomeTags.IS_TAIGA), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(BiomeTags.IS_MOUNTAIN).and(Predicate.not(isSnowing())), ModEntities.HILLS_CREEPER);
        addCreeper(tag(BiomeTags.IS_MOUNTAIN), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(IS_CAVE), ModEntities.DRIPSTONE_CREEPER);
        addCreeper(tag(IS_CAVE), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(BiomeTags.IS_JUNGLE), ModEntities.BAMBOO_CREEPER);
        addCreeper(tag(BiomeTags.IS_JUNGLE), ModEntities.JUNGLE_CREEPER);
        addCreeper(tag(BiomeTags.IS_JUNGLE), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(BiomeTags.HAS_RUINED_PORTAL_SWAMP), ModEntities.SWAMP_CREEPER);
        addCreeper(tag(BiomeTags.HAS_RUINED_PORTAL_SWAMP), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(IS_DARKFOREST), ModEntities.DARK_OAK_CREEPER);
        addCreeper(tag(IS_DARKFOREST), ModEntities.CAVE_CREEPER, 50);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), ModEntities.OCEAN_CREEPER.get().getCategory(), ModEntities.OCEAN_CREEPER.get(), 2, 1, 1);
        addCreeper(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), ModEntities.CAVE_CREEPER, 50);
    }

    public void removeCreepers() {
        Predicate<BiomeSelectionContext> creepersToRemove = tag(BiomeTags.IS_BEACH)
                .or(isSnowing())
                .or(tag(BiomeTags.HAS_DESERT_PYRAMID))
                .or(tag(BiomeTags.IS_HILL))
                .or(tag(BiomeTags.IS_SAVANNA))
                .or(tag(BiomeTags.HAS_MINESHAFT_MESA))
                .or(tag(IS_MUSHROOM))
                .or(tag(BiomeTags.IS_TAIGA))
                .or(tag(BiomeTags.IS_MOUNTAIN))
                .or(tag(IS_CAVE))
                .or(tag(BiomeTags.IS_JUNGLE))
                .or(tag(BiomeTags.HAS_RUINED_PORTAL_SWAMP))
                .or(tag(IS_DARKFOREST))
                .or(BiomeSelectors.includeByKey(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN));

        removeCreeper(creepersToRemove);
    }

    private static Predicate<BiomeSelectionContext> tag(TagKey<Biome> tag) {
        return BiomeSelectors.tag(tag);
    }

    private static Predicate<BiomeSelectionContext> isSnowing() {
        return ctx -> ctx.getBiome().getBaseTemperature() < 0.15f;
    }

    private <E extends BaseCreeper> void addCreeper(Predicate<BiomeSelectionContext> selectors, Supplier<EntityType<E>> entityType) {
        addCreeper(selectors, entityType, 75);
    }

    private <E extends BaseCreeper> void addCreeper(Predicate<BiomeSelectionContext> selectors, Supplier<EntityType<E>> entityType, int weight) {
        BiomeModifications.addSpawn(selectors.and(BiomeSelectors.foundInOverworld()), entityType.get().getCategory(), entityType.get(), weight, 2, 2);
    }

    private void removeCreeper(Predicate<BiomeSelectionContext> biomeSelector) {
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.CREEPER);

        BiomeModifications.create(id).add(ModificationPhase.REMOVALS, biomeSelector,
                context -> context.getSpawnSettings().removeSpawnsOfEntityType(EntityType.CREEPER));
    }
}
