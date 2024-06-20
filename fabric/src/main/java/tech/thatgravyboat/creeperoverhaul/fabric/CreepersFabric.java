package tech.thatgravyboat.creeperoverhaul.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
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

    private static final TagKey<Biome> IS_DARKFOREST = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("c", "is_dark_forest"));
    private static final TagKey<Biome> IS_MUSHROOM = TagKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath("c", "is_mushroom"));

    @Override
    public void onInitialize() {
        Creepers.init();
        FabricAttributes.register();
        Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes = new HashMap<>();
        Creepers.registerAttributes(attributes);
        attributes.forEach(FabricDefaultAttributeRegistry::register);
        addCreepers();
        removeCreepers();
        ModSpawns.addSpawnRules(new ModSpawns.Registrar() {
            @Override
            public <T extends Mob> void register(Supplier<EntityType<T>> entityType, SpawnPlacementType type, Heightmap.Types types, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
                SpawnPlacements.register(entityType.get(), type, types, spawnPredicate);
            }
        });

        ServerLifecycleEvents.SERVER_STARTING.register(event -> PluginLoader.load());
    }

    public void addCreepers() {
        addCreeper(tag(ConventionalBiomeTags.IS_BEACH), ModEntities.BEACH_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_BEACH), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(ConventionalBiomeTags.IS_SNOWY), ModEntities.SNOWY_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_SNOWY), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(ConventionalBiomeTags.IS_DESERT), ModEntities.DESERT_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_DESERT), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(ConventionalBiomeTags.IS_HILL), ModEntities.HILLS_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_HILL), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(ConventionalBiomeTags.IS_SAVANNA), ModEntities.SAVANNAH_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_SAVANNA), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(ConventionalBiomeTags.IS_BADLANDS), ModEntities.BADLANDS_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_BADLANDS), ModEntities.CAVE_CREEPER, 50);

        BiomeModifications.addSpawn(tag(IS_MUSHROOM), ModEntities.MUSHROOM_CREEPER.get().getCategory(), ModEntities.MUSHROOM_CREEPER.get(), 10, 1, 2);

        addCreeper(tag(ConventionalBiomeTags.IS_TAIGA), ModEntities.SPRUCE_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_TAIGA), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(ConventionalBiomeTags.IS_MOUNTAIN).and(tag(ConventionalBiomeTags.IS_SNOWY)), ModEntities.HILLS_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_MOUNTAIN), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(ConventionalBiomeTags.IS_CAVE), ModEntities.DRIPSTONE_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_CAVE), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(ConventionalBiomeTags.IS_JUNGLE), ModEntities.BAMBOO_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_JUNGLE), ModEntities.JUNGLE_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_JUNGLE), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(ConventionalBiomeTags.IS_SWAMP), ModEntities.SWAMP_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_SWAMP), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(IS_DARKFOREST), ModEntities.DARK_OAK_CREEPER);
        addCreeper(tag(IS_DARKFOREST), ModEntities.CAVE_CREEPER, 50);

        addCreeper(tag(ConventionalBiomeTags.IS_BIRCH_FOREST), ModEntities.BIRCH_CREEPER);
        addCreeper(tag(ConventionalBiomeTags.IS_BIRCH_FOREST), ModEntities.CAVE_CREEPER, 50);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), ModEntities.OCEAN_CREEPER.get().getCategory(), ModEntities.OCEAN_CREEPER.get(), 2, 1, 1);
        addCreeper(BiomeSelectors.includeByKey(Biomes.LUKEWARM_OCEAN, Biomes.WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN), ModEntities.CAVE_CREEPER, 50);
    }

    public void removeCreepers() {
        Predicate<BiomeSelectionContext> creepersToRemove = tag(ConventionalBiomeTags.IS_BEACH)
                .or(tag(ConventionalBiomeTags.IS_SNOWY))
                .or(tag(ConventionalBiomeTags.IS_DESERT))
                .or(tag(ConventionalBiomeTags.IS_HILL))
                .or(tag(ConventionalBiomeTags.IS_SAVANNA))
                .or(tag(ConventionalBiomeTags.IS_BADLANDS))
                .or(tag(IS_MUSHROOM))
                .or(tag(ConventionalBiomeTags.IS_TAIGA))
                .or(tag(ConventionalBiomeTags.IS_MOUNTAIN))
                .or(tag(ConventionalBiomeTags.IS_CAVE))
                .or(tag(ConventionalBiomeTags.IS_JUNGLE))
                .or(tag(ConventionalBiomeTags.IS_SWAMP))
                .or(tag(IS_DARKFOREST))
                .or(tag(ConventionalBiomeTags.IS_BIRCH_FOREST))
                .or(BiomeSelectors.includeByKey(Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN));

        removeCreeper(creepersToRemove);
    }

    private static Predicate<BiomeSelectionContext> tag(TagKey<Biome> tag) {
        return BiomeSelectors.tag(tag);
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
