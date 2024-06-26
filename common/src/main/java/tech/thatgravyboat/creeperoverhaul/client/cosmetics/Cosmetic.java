package tech.thatgravyboat.creeperoverhaul.client.cosmetics;

import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.util.RenderUtil;

public class Cosmetic implements GeoAnimatable {

    private static final RawAnimation ANIMATION = RawAnimation.begin().thenLoop("animation.idle");

    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private final CosmeticTexture texture;
    private final CosmeticModel model;
    private final CosmeticAnchor anchor;

    public Cosmetic(CosmeticTexture texture, CosmeticModel model, CosmeticAnchor anchor) {
        this.texture = texture;
        this.model = model;
        this.anchor = anchor;
    }

    public CosmeticTexture texture() {
        return this.texture;
    }

    public CosmeticModel model() {
        return this.model;
    }

    public CosmeticAnchor anchor() {
        return this.anchor;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> {
            event.getController().setAnimation(ANIMATION);
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.factory;
    }

    @Override
    public double getTick(Object object) {
        return RenderUtil.getCurrentTick();
    }
}
