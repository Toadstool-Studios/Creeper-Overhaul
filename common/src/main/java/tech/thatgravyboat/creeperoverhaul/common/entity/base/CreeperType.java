package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import java.util.*;
import java.util.function.*;

public record CreeperType(
        Function<BaseCreeper, ResourceLocation> texture,
        Function<BaseCreeper, ResourceLocation> glowingTexture,
        Function<BaseCreeper, ResourceLocation> chargedTexture,
        Function<BaseCreeper, ResourceLocation> model,
        Function<BaseCreeper, ResourceLocation> shearedModel,
        Function<BaseCreeper, ResourceLocation> animation,
        int melee,
        Map<Predicate<BlockState>, Function<RandomSource, BlockState>> replacer,
        Collection<EntityType<?>> entitiesAfraidOf,
        Collection<MobEffectInstance> inflictingPotions,
        Collection<MobEffectInstance> potionsWhenDead,
        Collection<Class<? extends LivingEntity>> entities,
        Collection<DamageSource> immunities,
        Consumer<CreeperAttributeBuilder> attributes,
        Supplier<ItemStack> shearDrop,

        Function<BaseCreeper, SoundEvent> deathSound,
        Function<BaseCreeper, SoundEvent> explosionSound,
        Function<BaseCreeper, SoundEvent> hitSound,
        Function<BaseCreeper, SoundEvent> hurtSound,
        Function<BaseCreeper, SoundEvent> primeSound,
        Function<BaseCreeper, SoundEvent> swimSound,
        Function<BaseCreeper, SoundEvent> flopSound,

        BooleanSupplier canSpawn
) {

    public Optional<SoundEvent> getDeathSound(BaseCreeper creeper) {
        return Optional.ofNullable(deathSound.apply(creeper));
    }

    public Optional<SoundEvent> getExplosionSound(BaseCreeper creeper) {
        return Optional.ofNullable(explosionSound.apply(creeper));
    }

    public Optional<SoundEvent> getHitSound(BaseCreeper creeper) {
        return Optional.ofNullable(hitSound.apply(creeper));
    }

    public Optional<SoundEvent> getHurtSound(BaseCreeper creeper) {
        return Optional.ofNullable(hurtSound.apply(creeper));
    }

    public Optional<SoundEvent> getPrimeSound(BaseCreeper creeper) {
        return Optional.ofNullable(primeSound.apply(creeper));
    }

    public Optional<SoundEvent> getSwimSound(BaseCreeper creeper) {
        return Optional.ofNullable(swimSound.apply(creeper));
    }

    public Optional<SoundEvent> getFlopSound(BaseCreeper creeper) {
        return Optional.ofNullable(flopSound.apply(creeper));
    }


    public boolean isShearable() {
        return shearDrop != null;
    }

    public static class Builder {
        private Function<BaseCreeper, ResourceLocation> texture;
        private Function<BaseCreeper, ResourceLocation> glowingTexture;
        private Function<BaseCreeper, ResourceLocation> chargedTexture;
        private Function<BaseCreeper, ResourceLocation> model;
        private Function<BaseCreeper, ResourceLocation> shearedModel;
        private Function<BaseCreeper, ResourceLocation> animation;
        private int melee = 0;
        private final List<EntityType<?>> afraidOf = new ArrayList<>();
        private final List<MobEffectInstance> inflictingPotions = new ArrayList<>();
        private final List<MobEffectInstance> potionsWhenDying = new ArrayList<>();
        private final List<Class<? extends LivingEntity>> attackingEntities = new ArrayList<>();
        private final List<DamageSource> immunities = new ArrayList<>();
        private final Map<Predicate<BlockState>, Function<RandomSource, BlockState>> replacer = new HashMap<>();
        private Consumer<CreeperAttributeBuilder> attributes = builder -> {};
        private Supplier<ItemStack> shearable = null;

        private Function<BaseCreeper, SoundEvent> deathSound = creeper -> SoundEvents.CREEPER_DEATH;
        private Function<BaseCreeper, SoundEvent> explosionSound = creeper -> null;
        private Function<BaseCreeper, SoundEvent> hitSound = creeper -> null;
        private Function<BaseCreeper, SoundEvent> hurtSound = creeper -> SoundEvents.CREEPER_HURT;
        private Function<BaseCreeper, SoundEvent> primeSound = creeper -> SoundEvents.CREEPER_PRIMED;
        private Function<BaseCreeper, SoundEvent> swimSound = creeper -> SoundEvents.GENERIC_SWIM;
        private Function<BaseCreeper, SoundEvent> flopSound = creeper -> SoundEvents.GUARDIAN_FLOP;

        private BooleanSupplier canSpawn = () -> true;

        public Builder setTexture(ResourceLocation texture) {
            this.texture = creeper -> texture;
            return this;
        }

        public Builder setTexture(Function<BaseCreeper, ResourceLocation> texture) {
            this.texture = texture;
            return this;
        }

        public Builder setGlowingTexture(Function<BaseCreeper, ResourceLocation> glowingTexture) {
            this.glowingTexture = glowingTexture;
            return this;
        }

        public Builder setGlowingTexture(ResourceLocation glowingTexture) {
            return setGlowingTexture(creeper -> glowingTexture);
        }

        public Builder setChargedTexture(Function<BaseCreeper, ResourceLocation> chargedTexture) {
            this.chargedTexture = chargedTexture;
            return this;
        }

        public Builder setChargedTexture(ResourceLocation chargedTexture) {
            return setChargedTexture(creeper -> chargedTexture);
        }

        public Builder setModel(Function<BaseCreeper, ResourceLocation> model) {
            this.model = model;
            return this;
        }

        public Builder setModel(ResourceLocation model) {
            return setModel(creeper -> model);
        }

        public Builder setShearedModel(Function<BaseCreeper, ResourceLocation> shearedModel) {
            this.shearedModel = shearedModel;
            return this;
        }

        public Builder setShearedModel(ResourceLocation shearedModel) {
            return setShearedModel(creeper -> shearedModel);
        }

        public Builder setAnimation(Function<BaseCreeper, ResourceLocation> animation) {
            this.animation = animation;
            return this;
        }

        public Builder setAnimation(ResourceLocation animation) {
            return setAnimation(creeper -> animation);
        }

        public Builder setMelee(int melee) {
            this.melee = melee;
            return this;
        }

        public Builder addReplacer(Predicate<BlockState> predicate, Function<RandomSource, BlockState> function) {
            this.replacer.put(predicate, function);
            return this;
        }

        public Builder addAfraidOf(EntityType<?> entity) {
            this.afraidOf.add(entity);
            return this;
        }

        public Builder addInflictingPotion(MobEffectInstance potion) {
            this.inflictingPotions.add(potion);
            return this;
        }

        public Builder addPotionsWhenDying(MobEffectInstance potion) {
            this.potionsWhenDying.add(potion);
            return this;
        }

        public Builder addAttackingEntities(Class<? extends LivingEntity> attackingEntities) {
            this.attackingEntities.add(attackingEntities);
            return this;
        }

        public Builder addImmunity(DamageSource source) {
            this.immunities.add(source);
            return this;
        }

        public Builder addAttributes(Consumer<CreeperAttributeBuilder> builder) {
            this.attributes = builder;
            return this;
        }

        public Builder setShearable(Supplier<ItemStack> shearable) {
            this.shearable = shearable;
            return this;
        }

        public Builder setDeathSound(Function<BaseCreeper, SoundEvent> deathSound) {
            this.deathSound = deathSound;
            return this;
        }

        public Builder setDeathSounds(Supplier<SoundEvent> sound) {
            return setDeathSound(creeper -> sound.get());
        }

        public Builder setExplosionSound(Function<BaseCreeper, SoundEvent> explosionSound) {
            this.explosionSound = explosionSound;
            return this;
        }

        public Builder setExplosionSounds(Supplier<SoundEvent> sound) {
            return setExplosionSound(creeper -> sound.get());
        }

        public Builder setHitSound(Function<BaseCreeper, SoundEvent> hitSound) {
            this.hitSound = hitSound;
            return this;
        }

        public Builder setHitSounds(Supplier<SoundEvent> sound) {
            return setHitSound(creeper -> sound.get());
        }

        public Builder setHurtSound(Function<BaseCreeper, SoundEvent> hurtSound) {
            this.hurtSound = hurtSound;
            return this;
        }

        public Builder setHurtSounds(Supplier<SoundEvent> sound) {
            return setHurtSound(creeper -> sound.get());
        }

        public Builder setPrimeSound(Function<BaseCreeper, SoundEvent> primeSound) {
            this.primeSound = primeSound;
            return this;
        }

        public Builder setPrimeSounds(Supplier<SoundEvent> sound) {
            return setPrimeSound(creeper -> sound.get());
        }

        public Builder setSwimSound(Function<BaseCreeper, SoundEvent> swimSound) {
            this.swimSound = swimSound;
            return this;
        }

        public Builder setSwimSounds(Supplier<SoundEvent> sound) {
            return setSwimSound(creeper -> sound.get());
        }

        public Builder setFlopSound(Function<BaseCreeper, SoundEvent> flopSound) {
            this.flopSound = flopSound;
            return this;
        }

        public Builder setFlopSounds(Supplier<SoundEvent> sound) {
            return setFlopSound(creeper -> sound.get());
        }

        public Builder setCanSpawn(BooleanSupplier canSpawn) {
            this.canSpawn = canSpawn;
            return this;
        }

        public CreeperType build() {
            return new CreeperType(texture, glowingTexture, chargedTexture, model, shearedModel, animation, melee, replacer, afraidOf, inflictingPotions, potionsWhenDying, attackingEntities, immunities, attributes, shearable, deathSound, explosionSound, hitSound, hurtSound, primeSound, swimSound, flopSound, canSpawn);
        }
    }

}
