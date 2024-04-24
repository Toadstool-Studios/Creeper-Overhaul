package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.creeperoverhaul.api.PluginRegistry;
import tech.thatgravyboat.creeperoverhaul.common.entity.goals.CreeperMeleeAttackGoal;

import java.util.UUID;

public class NeutralCreeper extends BaseCreeper implements NeutralMob {

    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(NeutralCreeper.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);

    @Nullable
    private UUID persistentTarget;

    public NeutralCreeper(EntityType<? extends NeutralCreeper> entityType, Level level, CreeperType type) {
        super(entityType, level, type);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        addPersistentAngerSaveData(compound);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        readPersistentAngerSaveData(this.level(), compound);
    }

    @Override
    protected void customServerAiStep() {
        if (this.level() instanceof ServerLevel serverLevel) {
            this.updatePersistentAnger(serverLevel, false);
        }
    }

    @Override
    protected void registerAttackGoals() {
        this.goalSelector.addGoal(4, new CreeperMeleeAttackGoal(this, 1.0, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, this::isAngryAt));
    }

    @Override
    public boolean canAttack(@NotNull LivingEntity entity) {
        if (!PluginRegistry.getInstance().canAttack(this, entity)) {
            return false;
        }
        return super.canAttack(entity);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    public boolean canSwell() {
        return super.canSwell() && (this.isAngry() || isIgnited());
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int time) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, time);
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return persistentTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID target) {
        persistentTarget = target;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }
}
