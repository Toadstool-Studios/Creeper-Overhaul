package tech.thatgravyboat.creeperoverhaul.common.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import tech.thatgravyboat.creeperoverhaul.common.config.SpawningConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

import java.util.function.Supplier;

public class ModSpawns {

    public static void addSpawnRules(Registrar registrar) {
        registrar.register(ModEntities.JUNGLE_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, getPredicate(CreeperTypes.JUNGLE, ModSpawns::checkMonsterSpawnRulesAbove));
        registrar.register(ModEntities.BAMBOO_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING, getPredicate(CreeperTypes.BAMBOO, ModSpawns::checkDayMonsterSpawnRulesAbove));
        registrar.register(ModEntities.DESERT_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.DESERT, ModSpawns::checkMonsterSpawnRulesAbove));
        registrar.register(ModEntities.BADLANDS_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.BADLANDS, ModSpawns::checkMonsterSpawnRulesAbove));
        registrar.register(ModEntities.HILLS_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.HILLS, ModSpawns::checkMonsterSpawnRulesAbove));
        registrar.register(ModEntities.SAVANNAH_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.SAVANNAH, ModSpawns::checkMonsterSpawnRulesAbove));
        registrar.register(ModEntities.MUSHROOM_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.MUSHROOM, ModSpawns::checkDayMonsterSpawnRulesAbove));
        registrar.register(ModEntities.SWAMP_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.SWAMP, ModSpawns::checkMonsterSpawnRulesAbove));
        registrar.register(ModEntities.DRIPSTONE_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.DRIPSTONE, ModSpawns::checkMonsterSpawnRulesCave));
        registrar.register(ModEntities.CAVE_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.CAVE, ModSpawns::checkMonsterSpawnRulesCave));
        registrar.register(ModEntities.DARK_OAK_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.DARK_OAK, ModSpawns::checkMonsterSpawnRulesAbove));
        registrar.register(ModEntities.SPRUCE_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.SPRUCE, ModSpawns::checkMonsterSpawnRulesAbove));
        registrar.register(ModEntities.BEACH_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.BEACH, ModSpawns::checkMonsterSpawnRulesAbove));
        registrar.register(ModEntities.SNOWY_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.SNOWY, ModSpawns::checkDayMonsterSpawnRulesAbove));
        registrar.register(ModEntities.OCEAN_CREEPER, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.OCEAN, ModSpawns::checkWaterSpawnRules));
        registrar.register(ModEntities.BIRCH_CREEPER, SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, getPredicate(CreeperTypes.BIRCH, ModSpawns::checkMonsterSpawnRulesAbove));
    }

    public static <T extends Mob> SpawnPlacements.SpawnPredicate<T> getPredicate(CreeperType creeper, SpawnPlacements.SpawnPredicate<T> predicate) {
        return (type, level, reason, pos, random) -> creeper.canSpawn().getAsBoolean() && SpawningConfig.allowSpawning && predicate.test(type, level, reason, pos, random);
    }

    public static boolean checkMonsterSpawnRulesCave(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pPos.getY() < pLevel.getSeaLevel() && !pLevel.getBlockState(pPos.below()).is(Blocks.GRASS_BLOCK) && pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(pLevel, pPos, pRandom) && Monster.checkMobSpawnRules(pType, pLevel, pReason, pPos, pRandom);
    }

    public static boolean checkMonsterSpawnRulesAbove(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        return pPos.getY() > pLevel.getSeaLevel() && pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.isDarkEnoughToSpawn(pLevel, pPos, pRandom) && Monster.checkMobSpawnRules(pType, pLevel, pReason, pPos, pRandom);
    }

    public static boolean checkWaterSpawnRules(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        int i = pLevel.getSeaLevel();
        int j = i - 13;
        return pPos.getY() >= j && pPos.getY() <= i && pLevel.getFluidState(pPos.below()).is(FluidTags.WATER) && pLevel.getBlockState(pPos.above()).is(Blocks.WATER);
    }

    public static boolean checkDayMonsterSpawnRulesAbove(EntityType<? extends Monster> pType, ServerLevelAccessor pLevel, MobSpawnType pReason, BlockPos pPos, RandomSource pRandom) {
        BlockState state = pLevel.getBlockState(pPos.below());
        boolean isGrassLike = state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.PODZOL) || state.is(Blocks.MYCELIUM) || state.is(BlockTags.DIRT);
        return pPos.getY() > pLevel.getSeaLevel() && pLevel.getDifficulty() != Difficulty.PEACEFUL && Monster.checkMobSpawnRules(pType, pLevel, pReason, pPos, pRandom) &&
                (isGrassLike || state.getBlock() instanceof LeavesBlock);
    }

    public interface Registrar {
        <T extends Mob> void register(Supplier<EntityType<T>> entityType, SpawnPlacementType type, Heightmap.Types types, SpawnPlacements.SpawnPredicate<T> spawnPredicate);
    }
}
