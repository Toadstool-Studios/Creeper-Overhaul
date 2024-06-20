package tech.thatgravyboat.creeperoverhaul.common.registry.neoforge;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.function.Supplier;

public class ModSpawnsImpl {
    public static <T extends Mob> void register(Supplier<EntityType<T>> entityType, SpawnPlacements.Type type, Heightmap.Types types, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
        SpawnPlacements.register(entityType.get(), type, types, spawnPredicate);
    }
}
