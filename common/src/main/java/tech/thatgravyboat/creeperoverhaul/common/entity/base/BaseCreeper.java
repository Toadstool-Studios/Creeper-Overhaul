package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.*;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.util.GeckoLibUtil;
import tech.thatgravyboat.creeperoverhaul.api.PluginRegistry;
import tech.thatgravyboat.creeperoverhaul.common.entity.goals.CreeperAvoidEntitiesGoal;
import tech.thatgravyboat.creeperoverhaul.common.entity.goals.CreeperMeleeAttackGoal;
import tech.thatgravyboat.creeperoverhaul.common.entity.goals.CreeperSwellGoal;
import tech.thatgravyboat.creeperoverhaul.common.utils.AnimationConstants;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

import java.util.Collection;
import java.util.stream.Stream;

public class BaseCreeper extends Creeper implements GeoEntity, Shearable {

    private static final EntityDataAccessor<Boolean> DATA_IS_ATTACKING = SynchedEntityData.defineId(BaseCreeper.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> DATA_IS_SHEARED = SynchedEntityData.defineId(BaseCreeper.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int oldSwell;
    private int swell;
    private int maxSwell = 30;
    public final CreeperType type;

    public BaseCreeper(EntityType<? extends Creeper> entityType, Level level, CreeperType type) {
        super(entityType, level);
        this.type = type;
        if (!level.isClientSide) {
            this.type.entities().forEach(e -> this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, e, true)));
        }
    }

    public static EntityType.EntityFactory<BaseCreeper> of(CreeperType type) {
        return (entityType, level) -> new BaseCreeper(entityType, level, type);
    }

    public static EntityType.EntityFactory<PassiveCreeper> ofPassive(CreeperType type) {
        return (entityType, level) -> new PassiveCreeper(entityType, level, type);
    }

    public static EntityType.EntityFactory<NeutralCreeper> ofNeutral(CreeperType type) {
        return (entityType, level) -> new NeutralCreeper(entityType, level, type);
    }

    //region Goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new CreeperSwellGoal(this));
        this.goalSelector.addGoal(3, new CreeperAvoidEntitiesGoal(this, 6.0F, 1.0, 1.2));
        registerMovementGoals();
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        registerAttackGoals();
        if (shouldRevenge()) this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    protected void registerAttackGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true, entity ->
                PluginRegistry.getInstance().canAttack(this, entity)
        ));
        this.goalSelector.addGoal(4, new CreeperMeleeAttackGoal(this, 1.0, false));
    }

    protected void registerMovementGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
    }
    //endregion

    //region State Management
    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_IS_ATTACKING, false);
        builder.define(DATA_IS_SHEARED, false);
    }

    public boolean isAttacking() {
        return this.getEntityData().get(DATA_IS_ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.getEntityData().set(DATA_IS_ATTACKING, attacking);
    }

    public boolean isSheared() {
        return this.entityData.get(DATA_IS_SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.getEntityData().set(DATA_IS_SHEARED, sheared);
    }
    //endregion

    //region NBT
    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putBoolean("Sheared", isSheared());
        tag.putShort("Fuse", (short)this.maxSwell);
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        setSheared(tag.getBoolean("Sheared"));
        if (tag.contains("Fuse", 99)) {
            this.maxSwell = tag.getShort("Fuse");
        }
    }
    //endregion

    //region Swelling
    public boolean causeFallDamage(float f, float g, @NotNull DamageSource source) {
        boolean bl = super.causeFallDamage(f, g, source);
        this.swell += (int)(f * 1.5F);
        if (this.swell > this.maxSwell - 5) {
            this.swell = this.maxSwell - 5;
        }

        return bl;
    }

    public void tick() {
        if (this.isAlive()) {
            this.oldSwell = this.swell;
            if (this.isIgnited()) {
                this.setSwellDir(1);
            }

            int i = this.getSwellDir();
            if (i > 0 && this.swell == 0) {
                this.playSound(type.getPrimeSound(this).orElse(SoundEvents.CREEPER_PRIMED), 1.0F, 0.5F);
                this.gameEvent(GameEvent.PRIME_FUSE);
            }

            this.swell += i;
            if (this.swell < 0) {
                this.swell = 0;
            }

            if (this.swell >= this.maxSwell) {
                this.swell = this.maxSwell;
                explode();
            }
        }

        super.tick();
    }

    public void explode() {
        if (!this.level().isClientSide) {
            Level.ExplosionInteraction interaction = PlatformUtils.getInteractionForCreeper(this);
            this.dead = true;
            Explosion explosion = this.level().explode(this, this.getX(), this.getY(), this.getZ(), 3f * (this.isPowered() ? 2.0f : 1.0f), interaction);

            type.getExplosionSound(this).ifPresent(s -> this.level().playSound(null, this, s,
                    SoundSource.BLOCKS, 4.0F, (1.0F + (this.level().random.nextFloat() - this.level().random.nextFloat()) * 0.2F) * 0.7F));

            this.discard();
            if (!this.type.inflictingPotions().isEmpty()) {
                explosion.getHitPlayers().keySet().forEach(player -> {
                    Collection<MobEffectInstance> inflictingPotions = this.type.inflictingPotions().stream().map(MobEffectInstance::new).toList();
                    inflictingPotions.forEach(player::addEffect);
                });
            }

            if (!this.type.replacer().isEmpty()) {

                final var entries = this.type.replacer().entrySet();

                explosion.getToBlow().stream()
                    .map(BlockPos::below)
                    .forEach(pos -> {
                        BlockState state = level().getBlockState(pos);
                        for (final var entry : entries) {
                            if (entry.getKey().test(state)) {
                                BlockState newState = entry.getValue().apply(this.random);
                                if (newState != null) {
                                    level().setBlock(pos, newState, Block.UPDATE_ALL);
                                    break;
                                }
                            }
                        }
                    });
            }

            var potions = Stream.concat(this.getActiveEffects().stream().map(MobEffectInstance::new), this.type.potionsWhenDead().stream().map(MobEffectInstance::new));
            summonCloudWithEffects(potions.toList());
        }
    }

    private void summonCloudWithEffects(Collection<MobEffectInstance> effects) {
        if (!effects.isEmpty()) {
            AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            cloud.setRadius(2.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setDuration(cloud.getDuration() / 2);
            cloud.setRadiusPerTick(-cloud.getRadius() / cloud.getDuration());
            effects.forEach(cloud::addEffect);

            this.level().addFreshEntity(cloud);
        }
    }

    @Override
    public float getSwelling(float f) {
        return Mth.lerp(f, (float)this.oldSwell, (float)this.swell) / (this.maxSwell - 2f);
    }

    public boolean canSwell() {
        return isExplodingCreeper();
    }

    public boolean isExplodingCreeper() {
        return this.type.melee() == 0;
    }
    //endregion

    //region Interactions

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
        if (isExplodingCreeper() && PlatformUtils.isFlintAndSteel(stack)) {
            this.level().playSound(player, this.blockPosition(), SoundEvents.FLINTANDSTEEL_USE, this.getSoundSource(), 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
            if (!this.level().isClientSide()) {
                ignite();
                stack.hurtAndBreak(1, player, slot);
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        if (readyForShearing() && PlatformUtils.isShears(stack)) {
            shear(SoundSource.AMBIENT);
            this.gameEvent(GameEvent.SHEAR, player);
            stack.hurtAndBreak(1, player, slot);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return InteractionResult.PASS;
    }
    //endregion

    //region Attacking
    public boolean shouldRevenge() {
        return true;
    }

    @Override
    public double getAttributeValue(Holder<Attribute> attribute) {
        return super.getAttributeValue(attribute) * (attribute.equals(Attributes.ATTACK_DAMAGE) && isPowered() ? 2 : 1);
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity entity) {
        if (canSwell()) return true;
        DamageSource damageSource = level().damageSources().mobAttack(this);
        float damage = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);
        if (level() instanceof ServerLevel serverLevel) {
            damage = EnchantmentHelper.modifyDamage(serverLevel, this.getWeaponItem(), entity, damageSource, damage);
        }

        if (entity.hurt(damageSource, damage)) {
            float knockback = getKnockback(entity, damageSource);
            if (knockback > 0.0 && entity instanceof LivingEntity living) {
                living.knockback(knockback * 0.5F, Mth.sin(this.getYRot() * 0.017453292F), -Mth.cos(this.getYRot() * 0.017453292F));
                this.setDeltaMovement(this.getDeltaMovement().multiply(0.6D, 1.0D, 0.6D));
            }

            if (level() instanceof ServerLevel serverLevel) {
                EnchantmentHelper.doPostAttackEffects(serverLevel, entity, damageSource);
            }
            this.setLastHurtMob(entity);
            return true;
        }

        return false;
    }

    @Override
    public float getSpeed() {
        return super.getSpeed() * (this.isPowered() ? 1.5F : 1.0F);
    }

    @Override
    public boolean isDamageSourceBlocked(@NotNull DamageSource source) {
        return super.isDamageSourceBlocked(source) || type.immunities().contains(source);
    }

    @Override
    protected @NotNull AABB getAttackBoundingBox() {
        AttributeInstance reachAttribute = this.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);
        double reach = reachAttribute != null ? reachAttribute.getValue() : 0;
        return super.getAttackBoundingBox().inflate(reach, 0, reach);
    }

    //endregion

    //region Sounds

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return type.getHurtSound(this).orElseGet(() -> super.getHurtSound(source));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return type.getDeathSound(this).orElseGet(super::getDeathSound);
    }

    @Override
    protected @NotNull SoundEvent getSwimSound() {
        return type.getSwimSound(this).orElseGet(super::getSwimSound);
    }

    //endregion

    //region Animation
    protected <E extends GeoAnimatable> PlayState idle(AnimationState<E> event) {
        event.getController().setAnimation(AnimationConstants.IDLE);
        return PlayState.CONTINUE;
    }

    protected <E extends GeoAnimatable> PlayState action(AnimationState<E> event) {
        AnimationProcessor.QueuedAnimation animation = event.getController().getCurrentAnimation();
        if (isAttacking()) {
            event.getController().setAnimation(AnimationConstants.ATTACK);
            return PlayState.CONTINUE;
        } else if (animation != null && animation.animation().name().equals("animation.creeper.attack") && event.getController().getAnimationState().equals(AnimationController.State.RUNNING)) {
            return PlayState.CONTINUE;
        } else if (event.isMoving()) {
            event.getController().setAnimation(AnimationConstants.WALK);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "action_controller", 3, this::action));
        controllers.add(new AnimationController<>(this, "idle_controller", 0, this::idle));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
    //endregion

    @Override
    public void shear(SoundSource soundSource) {
        this.level().playSound(null, this, SoundEvents.SNOW_GOLEM_SHEAR, soundSource, 1.0F, 1.0F);
        if (!this.level().isClientSide()) {
            this.spawnAtLocation(this.type.shearDrop().get(), 1.7F);
        }
    }

    @Override
    public boolean readyForShearing() {
        return !isSheared() && this.type.isShearable();
    }
}
