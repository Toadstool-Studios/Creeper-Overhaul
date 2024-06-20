package tech.thatgravyboat.creeperoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Supplier;

public class ModSounds {

    public static final ResourcefulRegistry<SoundEvent> SOUNDS = ResourcefulRegistries.create(BuiltInRegistries.SOUND_EVENT, Creepers.MODID);

    public static final Supplier<SoundEvent> PLANT_DEATH = registerSound("entity.plant.creeper.death");
    public static final Supplier<SoundEvent> PLANT_EXPLOSION = registerSound("entity.plant.creeper.explosion");
    public static final Supplier<SoundEvent> PLANT_HIT = registerSound("entity.plant.creeper.hit");
    public static final Supplier<SoundEvent> PLANT_HURT = registerSound("entity.plant.creeper.hurt");
    public static final Supplier<SoundEvent> PLANT_PRIME = registerSound("entity.plant.creeper.prime");

    public static final Supplier<SoundEvent> SAND_DEATH = registerSound("entity.sand.creeper.death");
    public static final Supplier<SoundEvent> SAND_EXPLOSION = registerSound("entity.sand.creeper.explosion");
    public static final Supplier<SoundEvent> SAND_HURT = registerSound("entity.sand.creeper.hurt");
    public static final Supplier<SoundEvent> SAND_PRIME = registerSound("entity.sand.creeper.prime");

    public static final Supplier<SoundEvent> STONE_DEATH = registerSound("entity.stone.creeper.death");
    public static final Supplier<SoundEvent> STONE_EXPLOSION = registerSound("entity.stone.creeper.explosion");
    public static final Supplier<SoundEvent> STONE_HURT = registerSound("entity.stone.creeper.hurt");
    public static final Supplier<SoundEvent> STONE_PRIME = registerSound("entity.stone.creeper.prime");

    public static final Supplier<SoundEvent> WOOD_DEATH = registerSound("entity.wood.creeper.death");
    public static final Supplier<SoundEvent> WOOD_EXPLOSION = registerSound("entity.wood.creeper.explosion");
    public static final Supplier<SoundEvent> WOOD_HIT = registerSound("entity.wood.creeper.hit");
    public static final Supplier<SoundEvent> WOOD_HURT = registerSound("entity.wood.creeper.hurt");
    public static final Supplier<SoundEvent> WOOD_PRIME = registerSound("entity.wood.creeper.prime");

    public static final Supplier<SoundEvent> OCEAN_DEATH = registerSound("entity.ocean.creeper.death");
    public static final Supplier<SoundEvent> OCEAN_HURT_INFLATED = registerSound("entity.ocean.creeper.hurt_inflated");
    public static final Supplier<SoundEvent> OCEAN_HURT_DEFLATED = registerSound("entity.ocean.creeper.hurt_deflated");
    public static final Supplier<SoundEvent> OCEAN_INFLATE = registerSound("entity.ocean.creeper.inflate");
    public static final Supplier<SoundEvent> OCEAN_DEFLATE = registerSound("entity.ocean.creeper.deflate");

    private static Supplier<SoundEvent> registerSound(String id) {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(Creepers.id(id)));
    }
}
