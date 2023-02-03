package tech.thatgravyboat.creeperoverhaul.common.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Supplier;

public class ModSounds {

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

    public static void init() {
        // init class
    }

    private static Supplier<SoundEvent> registerSound(String id) {
        return registerSound(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Creepers.MODID, id)));
    }

    @ExpectPlatform
    public static Supplier<SoundEvent> registerSound(String id, Supplier<SoundEvent> sound) {
        throw new AssertionError();
    }
}
