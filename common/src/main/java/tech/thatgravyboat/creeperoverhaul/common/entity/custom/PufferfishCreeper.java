package tech.thatgravyboat.creeperoverhaul.common.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.creeperoverhaul.api.PluginRegistry;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.WaterCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.goals.CreeperMeleeAttackGoal;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSounds;

import java.util.List;

public class PufferfishCreeper extends WaterCreeper {

    private static final TargetingConditions TARGETS = TargetingConditions.forNonCombat().ignoreInvisibilityTesting()
            .ignoreLineOfSight()
            .selector((entity) -> entity.getMobType() != MobType.WATER && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(entity) && entity.isAlive());

    private static final List<EntityDimensions> DIMENSIONS = List.of(
            EntityDimensions.scalable(0.6875f, 1.125f),
            EntityDimensions.scalable(0.625f, 0.9375f),
            EntityDimensions.scalable(1f, 1.375f)
    );

    private static final EntityDataAccessor<Byte> VARIANT = SynchedEntityData.defineId(PufferfishCreeper.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Byte> PUFF_STATE = SynchedEntityData.defineId(PufferfishCreeper.class, EntityDataSerializers.BYTE);

    private int inflateCounter;
    private int deflateTimer;

    public PufferfishCreeper(EntityType<? extends PufferfishCreeper> entityType, Level level, CreeperType type) {
        super(entityType, level, type);
    }

    public static EntityType.EntityFactory<PufferfishCreeper> ofPufferfish(CreeperType type) {
        return (entityType, level) -> new PufferfishCreeper(entityType, level, type);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor level, @NotNull DifficultyInstance difficulty, @NotNull MobSpawnType type, @Nullable SpawnGroupData group, @Nullable CompoundTag tag) {
        setVariant(level.getRandom().nextBoolean() ? Variant.TEAL : Variant.BROWN);
        return super.finalizeSpawn(level, difficulty, type, group, tag);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new PuffGoal(this));
    }

    @Override
    protected void registerAttackGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, entity ->
            PluginRegistry.getInstance().canAttack(this, entity)
        ));
        this.goalSelector.addGoal(4, new CreeperMeleeAttackGoal(this, 3.0, false));
    }

    //region Data
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, (byte) 0);
        this.entityData.define(PUFF_STATE, (byte) 0);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> accessor) {
        super.onSyncedDataUpdated(accessor);
        if (VARIANT.equals(accessor)) {
            this.refreshDimensions();
        }
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.entityData.set(VARIANT, tag.getByte("Variant") == 0 ? (byte) 0 : (byte) 1);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putByte("Variant", this.entityData.get(VARIANT));
    }

    public byte getPuffState() {
        return this.entityData.get(PUFF_STATE);
    }

    public void setPuffState(int puffState) {
        this.entityData.set(PUFF_STATE, (byte)puffState);
    }

    public Variant getVariant() {
        return Variant.byId(this.entityData.get(VARIANT));
    }

    public void setVariant(Variant variant) {
        this.entityData.set(VARIANT, (byte) variant.ordinal());
    }

    public int getPuffId() {
        return switch (this.getPuffState()) {
            case 0 -> 1;
            case 1 -> 2;
            default -> 3;
        };
    }
    //endregion

    @Override
    public void tick() {
        if (!this.level().isClientSide && this.isAlive() && this.isEffectiveAi()) {
            if (this.inflateCounter > 0) {
                if (this.getPuffState() == 0) {
                    this.playSound(ModSounds.OCEAN_INFLATE.get(), this.getSoundVolume(), this.getVoicePitch());
                    this.setPuffState(1);
                } else if (this.inflateCounter > 40 && this.getPuffState() == 1) {
                    this.playSound(ModSounds.OCEAN_INFLATE.get(), this.getSoundVolume(), this.getVoicePitch());
                    this.setPuffState(2);
                }

                ++this.inflateCounter;
            } else if (this.getPuffState() != 0) {
                if (this.deflateTimer > 60 && this.getPuffState() == 2) {
                    this.playSound(ModSounds.OCEAN_DEFLATE.get(), this.getSoundVolume(), this.getVoicePitch());
                    this.setPuffState(1);
                } else if (this.deflateTimer > 100 && this.getPuffState() == 1) {
                    this.playSound(ModSounds.OCEAN_DEFLATE.get(), this.getSoundVolume(), this.getVoicePitch());
                    this.setPuffState(0);
                }

                ++this.deflateTimer;
            }
        }

        super.tick();
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        byte state = this.getPuffState();
        if (entity.hurt(level().damageSources().mobAttack(this), (6f + state) * (this.isPowered() ? 2f : 1f))) {
            this.playSound(SoundEvents.PUFFER_FISH_STING, 1.0F, 1.0F);
            if (entity instanceof LivingEntity living) {
                living.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * state, 0));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean canSwell() {
        return false;
    }

    private boolean canTouch(LivingEntity entity) {
        return TARGETS.test(this, entity);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return DIMENSIONS.get(this.getPuffId() - 1);
    }

    public enum Variant {
        TEAL,
        BROWN;

        public static Variant byId(byte id) {
            return id == 0 ? TEAL : BROWN;
        }
    }

    public static class PuffGoal extends Goal {
        private final PufferfishCreeper creeper;

        public PuffGoal(PufferfishCreeper creeper) {
            this.creeper = creeper;
        }

        @Override
        public boolean canUse() {
            List<Player> list = this.creeper.level().getEntitiesOfClass(Player.class, this.creeper.getBoundingBox().inflate(6.0), creeper::canTouch);
            return !list.isEmpty();
        }

        @Override
        public void start() {
            this.creeper.inflateCounter = 1;
            this.creeper.deflateTimer = 0;
        }

        @Override
        public void stop() {
            this.creeper.inflateCounter = 0;
        }
    }
}
