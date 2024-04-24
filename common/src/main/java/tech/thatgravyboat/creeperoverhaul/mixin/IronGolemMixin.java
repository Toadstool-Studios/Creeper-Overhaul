package tech.thatgravyboat.creeperoverhaul.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.IronGolem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.PassiveCreeper;

@Mixin(IronGolem.class)
public abstract class IronGolemMixin {

    @Inject(
            method = "method_6498",
            at = @At("HEAD"),
            cancellable = true
    )
    private static void creeperoverhaul$onRegisterAttackGoal(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof BaseCreeper creeper) {
            cir.setReturnValue(!(creeper instanceof PassiveCreeper) && !creeper.canSwell());
        }
    }
}
