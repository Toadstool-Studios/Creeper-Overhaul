package tech.thatgravyboat.creeperoverhaul.common.utils;

import software.bernie.geckolib.animation.RawAnimation;

public final class AnimationConstants {

    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.creeper.walk");
    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.creeper.idle");
    public static final RawAnimation ATTACK = RawAnimation.begin().thenPlay("animation.creeper.attack");
    public static final RawAnimation FLOP = RawAnimation.begin().thenLoop("animation.creeper.flop");
    public static final RawAnimation SWIM = RawAnimation.begin().thenLoop("animation.creeper.swim");

}
