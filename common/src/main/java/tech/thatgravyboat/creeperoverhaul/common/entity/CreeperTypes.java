package tech.thatgravyboat.creeperoverhaul.common.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Stray;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.config.SpawningConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;
import tech.thatgravyboat.creeperoverhaul.common.entity.custom.PufferfishCreeper;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModItems;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSounds;

import java.util.Locale;

public class CreeperTypes {

    public static final CreeperType JUNGLE = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/jungle/jungle_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/jungle/jungle_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor.png"))
            .setModel(Creepers.id("geo/jungle.geo.json"))
            .setAnimation(Creepers.id("animations/jungle.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowJungleCreeperSpawning)
            .build();

    public static final CreeperType BAMBOO = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/bamboo/bamboo_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/bamboo/bamboo_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor.png"))
            .setModel(Creepers.id("geo/bamboo.geo.json"))
            .setAnimation(Creepers.id("animations/bamboo.animation.json"))
            .setMelee(9)
            .addAfraidOf(EntityType.PANDA)
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 20);
                builder.add(Attributes.ATTACK_DAMAGE, 2);
                builder.add(Attributes.ENTITY_INTERACTION_RANGE, 2);
            })
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHitSounds(ModSounds.PLANT_HIT)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowBambooCreeperSpawning)
            .build();

    public static final CreeperType DESERT = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/desert/desert_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/desert/desert_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("geo/desert.geo.json"))
            .setShearedModel(Creepers.id("geo/desert_sheared.geo.json"))
            .setShearable(() -> new ItemStack(ModItems.TINY_CACTUS.get()))
            .setAnimation(Creepers.id("animations/desert.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            //.addImmunity(DamageSources)
            .addAttributes(builder -> builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5))
            .setDeathSounds(ModSounds.SAND_DEATH)
            .setExplosionSounds(ModSounds.SAND_EXPLOSION)
            .setHurtSounds(ModSounds.SAND_HURT)
            .setPrimeSounds(ModSounds.SAND_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowDesertCreeperSpawning)
            .build();

    public static final CreeperType BADLANDS = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/badlands/badlands_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/badlands/badlands_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("geo/badlands.geo.json"))
            .setShearedModel(Creepers.id("geo/badlands_sheared.geo.json"))
            .setShearable(() -> new ItemStack(ModItems.TINY_CACTUS.get()))
            .setAnimation(Creepers.id("animations/badlands.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            //.addImmunity(DamageSource.CACTUS)
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 30);
                builder.add(Attributes.KNOCKBACK_RESISTANCE, 1);
            })
            .setDeathSounds(ModSounds.SAND_DEATH)
            .setExplosionSounds(ModSounds.SAND_EXPLOSION)
            .setHurtSounds(ModSounds.SAND_HURT)
            .setPrimeSounds(ModSounds.SAND_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowBadlandsCreeperSpawning)
            .build();

    public static final CreeperType HILLS = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/hills/hills_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/hills/hills_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("geo/hills.geo.json"))
            .setAnimation(Creepers.id("animations/hills.animation.json"))
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 30);
                builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
            })
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowHillsCreeperSpawning)
            .build();

    public static final CreeperType SAVANNAH = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/savannah/savannah_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/savannah/savannah_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_3.png"))
            .setModel(Creepers.id("geo/savannah.geo.json"))
            .setAnimation(Creepers.id("animations/savannah.animation.json"))
            .setMelee(5)
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 25);
                builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
                builder.add(Attributes.ATTACK_DAMAGE, 3);
            })
            .setDeathSounds(ModSounds.WOOD_DEATH)
            .setExplosionSounds(ModSounds.WOOD_EXPLOSION)
            .setHurtSounds(ModSounds.WOOD_HURT)
            .setHitSounds(ModSounds.WOOD_HIT)
            .setPrimeSounds(ModSounds.WOOD_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowSavannahCreeperSpawning)
            .build();

    public static final CreeperType MUSHROOM = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/mushroom/mushroom_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/mushroom/mushroom_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_4.png"))
            .setModel(Creepers.id("geo/mushroom.geo.json"))
            .setAnimation(Creepers.id("animations/mushroom.animation.json"))
            .addPotionsWhenDying(new MobEffectInstance(MobEffects.POISON, 100, 1))
            .addReplacer(state -> state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT), random -> random.nextInt(3) == 0 ? Blocks.MYCELIUM.defaultBlockState() : null)
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowMushroomCreeperSpawning)
            .build();

    public static final CreeperType SWAMP = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/swamp/swamp_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/swamp/swamp_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor.png"))
            .setModel(Creepers.id("geo/swamp.geo.json"))
            .setAnimation(Creepers.id("animations/swamp.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttributes(builder -> builder.add("swim_speed", 2))
            .setDeathSounds(ModSounds.PLANT_DEATH)
            .setExplosionSounds(ModSounds.PLANT_EXPLOSION)
            .setHurtSounds(ModSounds.PLANT_HURT)
            .setPrimeSounds(ModSounds.PLANT_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowSwampCreeperSpawning)
            .build();

    public static final CreeperType DRIPSTONE = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/dripstone/dripstone_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/dripstone/dripstone_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("geo/dripstone.geo.json"))
            .setAnimation(Creepers.id("animations/dripstone.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttributes(builder -> builder.add(Attributes.MAX_HEALTH, 12))
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowDripstoneCreeperSpawning)
            .build();

    public static final CreeperType CAVE = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/cave/cave_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/cave/cave_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("geo/cave.geo.json"))
            .setAnimation(Creepers.id("animations/cave.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 25);
                builder.add(Attributes.KNOCKBACK_RESISTANCE, 0.5);
            })
            .addReplacer(state -> state.is(Blocks.STONE), random -> random.nextInt(100) == 0 ? Blocks.COAL_ORE.defaultBlockState() : null)
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowCaveCreeperSpawning)
            .build();

    public static final CreeperType DARK_OAK = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/dark_oak/dark_oak_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/dark_oak/dark_oak_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_3.png"))
            .setModel(Creepers.id("geo/dark_oak.geo.json"))
            .setAnimation(Creepers.id("animations/dark_oak.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addInflictingPotion(new MobEffectInstance(MobEffects.BLINDNESS, 100, 0))
            .setDeathSounds(ModSounds.WOOD_DEATH)
            .setExplosionSounds(ModSounds.WOOD_EXPLOSION)
            .setHurtSounds(ModSounds.WOOD_HURT)
            .setPrimeSounds(ModSounds.WOOD_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowDarkOakCreeperSpawning)
            .build();

    public static final CreeperType SPRUCE = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/spruce/spruce_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/spruce/spruce_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("geo/spruce.geo.json"))
            .setAnimation(Creepers.id("animations/spruce.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addReplacer(state -> state.is(Blocks.GRASS_BLOCK) || state.is(Blocks.DIRT), random -> random.nextInt(3) == 0 ? Blocks.PODZOL.defaultBlockState() : null)
            .setDeathSounds(ModSounds.STONE_DEATH)
            .setExplosionSounds(ModSounds.STONE_EXPLOSION)
            .setHurtSounds(ModSounds.STONE_HURT)
            .setPrimeSounds(ModSounds.STONE_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowSpruceCreeperSpawning)
            .build();

    public static final CreeperType BEACH = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/beach/beach_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/beach/beach_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_2.png"))
            .setModel(Creepers.id("geo/beach.geo.json"))
            .setAnimation(Creepers.id("animations/beach.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            //.addImmunity(DamageSource.DROWN)
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 15);
                builder.add("swim_speed", 2);
            })
            .setDeathSounds(ModSounds.SAND_DEATH)
            .setExplosionSounds(ModSounds.SAND_EXPLOSION)
            .setHurtSounds(ModSounds.SAND_HURT)
            .setPrimeSounds(ModSounds.SAND_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowBeachCreeperSpawning)
            .build();

    public static final CreeperType SNOWY = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/snowy/snowy_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/snowy/snowy_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor.png"))
            .setModel(Creepers.id("geo/snowy.geo.json"))
            .setAnimation(Creepers.id("animations/snowy.animation.json"))
            .setMelee(5)
            .addAttributes(builder -> builder.add(Attributes.ATTACK_DAMAGE, 4))
            .addAttackingEntities(Stray.class)
            .setCanSpawn(() -> SpawningConfig.allowSnowyCreeperSpawning)
            .build();

    public static final CreeperType OCEAN = new CreeperType.Builder()
            .setTexture(creeper -> {
                if (creeper instanceof PufferfishCreeper pufferfish) {
                    int id = pufferfish.getPuffId();
                    PufferfishCreeper.Variant variant = pufferfish.getVariant();
                    return Creepers.id("textures/entity/ocean/" + variant.name().toLowerCase(Locale.ROOT) + "_" + id + ".png");
                }
                return Creepers.id("textures/entity/ocean/brown_1.png");
            })
            .setGlowingTexture(Creepers.id("textures/entity/ocean/glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_4.png"))
            .setModel(creeper -> {
                if (creeper instanceof PufferfishCreeper pufferfish) {
                    int id = pufferfish.getPuffId();
                    return Creepers.id("geo/ocean_" + id + ".geo.json");
                }
                return Creepers.id("geo/ocean_1.geo.json");
            })
            .setAnimation(Creepers.id("animations/ocean.animation.json"))
            .addAttributes(builder -> {
                builder.add(Attributes.MAX_HEALTH, 16);
                builder.add(Attributes.MOVEMENT_SPEED, 1.7);
                builder.add(Attributes.ENTITY_INTERACTION_RANGE, 2.5);
                builder.add("swim_speed", 1.7);
            })
            .setDeathSounds(ModSounds.OCEAN_DEATH)
            .setHurtSound(creeper -> {
                if (creeper instanceof PufferfishCreeper fish && fish.getPuffId() == 3) {
                    return ModSounds.OCEAN_HURT_INFLATED.get();
                }
                return ModSounds.OCEAN_HURT_DEFLATED.get();
            })
            .setFlopSounds(() -> SoundEvents.PUFFER_FISH_FLOP)
            .setCanSpawn(() -> SpawningConfig.allowOceanCreeperSpawning)
            .build();

    public static final CreeperType BIRCH = new CreeperType.Builder()
            .setTexture(Creepers.id("textures/entity/birch/birch_creeper.png"))
            .setGlowingTexture(Creepers.id("textures/entity/birch/birch_creeper_glow.png"))
            .setChargedTexture(Creepers.id("textures/entity/armor/creeper_armor_3.png"))
            .setModel(Creepers.id("geo/birch.geo.json"))
            .setAnimation(Creepers.id("animations/birch.animation.json"))
            .addAfraidOf(EntityType.CAT)
            .addAfraidOf(EntityType.OCELOT)
            .addAttributes(builder -> builder.add(Attributes.MAX_HEALTH, 20))
            .setDeathSounds(ModSounds.WOOD_DEATH)
            .setExplosionSounds(ModSounds.WOOD_EXPLOSION)
            .setHurtSounds(ModSounds.WOOD_HURT)
            .setPrimeSounds(ModSounds.WOOD_PRIME)
            .setCanSpawn(() -> SpawningConfig.allowBirchCreeperSpawning)
            .build();
}
