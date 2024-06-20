package tech.thatgravyboat.creeperoverhaul.mixin.fabric;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.registry.fabric.FabricAttributes;

@Mixin(BaseCreeper.class)
public abstract class BaseCreeperMixin extends LivingEntity {

    protected BaseCreeperMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void goDownInWater() {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.04D * this.getAttributeValue(FabricAttributes.getSwimSpeed()), 0.0D));
    }

    @Override
    protected void jumpInLiquid(@NotNull TagKey<Fluid> fluid) {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0D, 0.04D * this.getAttributeValue(FabricAttributes.getSwimSpeed()), 0.0D));
    }

    @Override
    public void moveRelative(float speed, @NotNull Vec3 movementInput) {
        FluidState fluidState = this.level().getFluidState(this.blockPosition());
        if (this.isInWater() && this.isAffectedByFluids() && !this.canStandOnFluid(fluidState)){
            super.moveRelative(speed * (float)this.getAttributeValue(FabricAttributes.getSwimSpeed()), movementInput);
            return;
        }
        super.moveRelative(speed, movementInput);
    }
}
