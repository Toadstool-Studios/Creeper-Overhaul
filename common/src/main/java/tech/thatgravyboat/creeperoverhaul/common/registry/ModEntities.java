package tech.thatgravyboat.creeperoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.CreeperTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.NeutralCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.PassiveCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.custom.PufferfishCreeper;

import java.util.function.Supplier;

public class ModEntities {

    public static final ResourcefulRegistry<EntityType<?>> ENTITIES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, Creepers.MODID);

    public static final Supplier<EntityType<BaseCreeper>> JUNGLE_CREEPER = ENTITIES.register("jungle_creeper",
            () -> EntityType.Builder.of(BaseCreeper.of(CreeperTypes.JUNGLE), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("jungle_creeper"));

    public static final Supplier<EntityType<NeutralCreeper>> BAMBOO_CREEPER = ENTITIES.register("bamboo_creeper",
            () -> EntityType.Builder.of(BaseCreeper.ofNeutral(CreeperTypes.BAMBOO), MobCategory.MONSTER).sized(0.6F, 2F)
                    .clientTrackingRange(8).build("bamboo_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> DESERT_CREEPER = ENTITIES.register("desert_creeper",
            () -> EntityType.Builder.of(BaseCreeper.of(CreeperTypes.DESERT), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("desert_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> BADLANDS_CREEPER = ENTITIES.register("badlands_creeper",
            () -> EntityType.Builder.of(BaseCreeper.of(CreeperTypes.BADLANDS), MobCategory.MONSTER).sized(1F, 1.8F)
                    .clientTrackingRange(8).build("badlands_creeper"));

    public static final Supplier<EntityType<NeutralCreeper>> HILLS_CREEPER = ENTITIES.register("hills_creeper",
            () -> EntityType.Builder.of(BaseCreeper.ofNeutral(CreeperTypes.HILLS), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("hills_creeper"));

    public static final Supplier<EntityType<NeutralCreeper>> SAVANNAH_CREEPER = ENTITIES.register("savannah_creeper",
            () -> EntityType.Builder.of(BaseCreeper.ofNeutral(CreeperTypes.SAVANNAH), MobCategory.MONSTER).sized(0.6F, 2.2F)
                    .clientTrackingRange(8).build("savannah_creeper"));

    public static final Supplier<EntityType<PassiveCreeper>> MUSHROOM_CREEPER = ENTITIES.register("mushroom_creeper",
            () -> EntityType.Builder.of(BaseCreeper.ofPassive(CreeperTypes.MUSHROOM), MobCategory.CREATURE).sized(1F, 1.7F)
                    .clientTrackingRange(8).build("mushroom_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> SWAMP_CREEPER = ENTITIES.register("swamp_creeper",
            () -> EntityType.Builder.of(BaseCreeper.of(CreeperTypes.SWAMP), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("swamp_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> DRIPSTONE_CREEPER = ENTITIES.register("dripstone_creeper",
            () -> EntityType.Builder.of(BaseCreeper.of(CreeperTypes.DRIPSTONE), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("dripstone_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> CAVE_CREEPER = ENTITIES.register("cave_creeper",
            () -> EntityType.Builder.of(BaseCreeper.of(CreeperTypes.CAVE), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("cave_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> DARK_OAK_CREEPER = ENTITIES.register("dark_oak_creeper",
            () -> EntityType.Builder.of(BaseCreeper.of(CreeperTypes.DARK_OAK), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("dark_oak_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> SPRUCE_CREEPER = ENTITIES.register("spruce_creeper",
            () -> EntityType.Builder.of(BaseCreeper.of(CreeperTypes.SPRUCE), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("spruce_creeper"));

    public static final Supplier<EntityType<BaseCreeper>> BEACH_CREEPER = ENTITIES.register("beach_creeper",
            () -> EntityType.Builder.of(BaseCreeper.of(CreeperTypes.BEACH), MobCategory.MONSTER).sized(0.6F, 1.7F)
                    .clientTrackingRange(8).build("beach_creeper"));

    public static final Supplier<EntityType<NeutralCreeper>> SNOWY_CREEPER = ENTITIES.register("snowy_creeper",
            () -> EntityType.Builder.of(BaseCreeper.ofNeutral(CreeperTypes.SNOWY), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("snowy_creeper"));

    public static final Supplier<EntityType<PufferfishCreeper>> OCEAN_CREEPER = ENTITIES.register("ocean_creeper",
            () -> EntityType.Builder.of(PufferfishCreeper.ofPufferfish(CreeperTypes.OCEAN), MobCategory.MONSTER).sized(0.7F, 1.7F)
                    .clientTrackingRange(8).build("ocean_creeper"));
}
