package tech.thatgravyboat.creeperoverhaul.common.entity.goals;

import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.WaterCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

public class WaterCreeperMoveControl extends MoveControl {
    private final WaterCreeper creeper;

    public WaterCreeperMoveControl(WaterCreeper creeper) {
        super(creeper);
        this.creeper = creeper;
    }

    public void tick() {
        if (this.creeper.isEyeInFluid(FluidTags.WATER)) {
            this.creeper.setDeltaMovement(this.creeper.getDeltaMovement().add(0.0, 0.0025, 0.0));
        }

        if (this.operation == MoveControl.Operation.MOVE_TO && !this.creeper.getNavigation().isDone()) {
            float f = (float) (this.speedModifier * this.creeper.getAttributeValue(PlatformUtils.getModAttribute("swim_speed")));
            this.creeper.setSpeed(Mth.lerp(0.125F, this.creeper.getSpeed(), f));
            double d = this.wantedX - this.creeper.getX();
            double e = this.wantedY - this.creeper.getY();
            double g = this.wantedZ - this.creeper.getZ();
            if (e != 0.0) {
                double h = Math.sqrt(d * d + e * e + g * g);
                this.creeper.setDeltaMovement(this.creeper.getDeltaMovement().add(0.0, (double) this.creeper.getSpeed() * (e / h) * 0.05, 0.0));
            }

            if (d != 0.0 || g != 0.0) {
                float i = (float) (Mth.atan2(g, d) * 57.2957763671875) - 90.0F;
                this.creeper.setYRot(this.rotlerp(this.creeper.getYRot(), i, 90.0F));
                this.creeper.yBodyRot = this.creeper.getYRot();
            }

        } else {
            this.creeper.setSpeed(0.0F);
        }
    }
}