package tech.thatgravyboat.creeperoverhaul.common.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

public class CreeperMeleeAttackGoal extends MeleeAttackGoal {

    private final BaseCreeper creeper;

    public CreeperMeleeAttackGoal(BaseCreeper creeper, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(creeper, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.creeper = creeper;
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity enemy, double pDistToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if (pDistToEnemySqr <= d0 && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            this.creeper.type.getHitSound(this.creeper).ifPresent(s -> this.creeper.level().playSound(null, this.creeper, s, this.creeper.getSoundSource(), 0.5F, 1.0F));
            this.mob.doHurtTarget(enemy);
        } else if (pDistToEnemySqr <= d0) {
            creeper.setAttacking(true);
        } else {
            this.resetAttackCooldown();
            creeper.setAttacking(false);
        }
    }

    @Override
    protected int adjustedTickDelay(int p_186072_) {
        return super.adjustedTickDelay(p_186072_) + creeper.type.melee() - 4;
    }

    public void stop() {
        creeper.setAttacking(false);
        super.stop();
    }

    @Override
    protected double getAttackReachSqr(@NotNull LivingEntity target) {
        AttributeInstance attribute = this.creeper.getAttribute(PlatformUtils.getModAttribute("reach_distance"));
        return super.getAttackReachSqr(target) + (attribute != null ? attribute.getValue() : 0);
    }
}
