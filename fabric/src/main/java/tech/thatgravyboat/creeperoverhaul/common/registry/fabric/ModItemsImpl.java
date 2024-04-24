package tech.thatgravyboat.creeperoverhaul.common.registry.fabric;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class ModItemsImpl {

    public static <E extends Mob, T extends EntityType<E>>  SpawnEggItem createSpawnEgg(Supplier<T> entity, int primaryColor, int secondaryColor, Item.Properties properties) {
        return new SpawnEggItem(entity.get(), primaryColor, secondaryColor, properties);
    }
}
