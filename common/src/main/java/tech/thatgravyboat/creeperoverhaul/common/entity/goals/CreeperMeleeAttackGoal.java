package tech.thatgravyboat.creeperoverhaul.common.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import org.jetbrains.annotations.NotNull;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

public class CreeperMeleeAttackGoal extends MeleeAttackGoal {

    private final BaseCreeper creeper;

    public CreeperMeleeAttackGoal(BaseCreeper creeper, double pSpeedModifier, boolean pFollowingTargetEvenIfNotSeen) {
        super(creeper, pSpeedModifier, pFollowingTargetEvenIfNotSeen);
        this.creeper = creeper;
    }

    @Override
    protected void checkAndPerformAttack(@NotNull LivingEntity enemy) {
        if (this.mob.isWithinMeleeAttackRange(enemy) && this.isTimeToAttack()) {
            this.resetAttackCooldown();
            this.creeper.type.getHitSound(this.creeper).ifPresent(s -> this.creeper.level().playSound(null, this.creeper, s, this.creeper.getSoundSource(), 0.5F, 1.0F));
            this.mob.doHurtTarget(enemy);
        } else if (this.mob.isWithinMeleeAttackRange(enemy)) {
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
}
