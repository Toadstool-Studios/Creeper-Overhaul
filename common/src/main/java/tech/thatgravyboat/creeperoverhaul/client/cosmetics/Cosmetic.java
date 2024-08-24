package tech.thatgravyboat.creeperoverhaul.client.cosmetics;

import com.google.gson.JsonObject;
import net.minecraft.util.GsonHelper;
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

    private final String id;
    private final String name;
    private final CosmeticTexture texture;
    private final CosmeticModel model;
    private final CosmeticAnchor anchor;

    public Cosmetic(String id, String name, CosmeticTexture texture, CosmeticModel model, CosmeticAnchor anchor) {
        this.id = id;
        this.name = name;
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

    public String id() {
        return this.id;
    }

    public String name() {
        return this.name;
    }

    public static Cosmetic fromJson(String id, JsonObject json) {
        String name = GsonHelper.getAsString(json, "name", id);
        CosmeticTexture texture = new CosmeticTexture(json.get("texture").getAsString());
        CosmeticGeoModel model = new CosmeticGeoModel(json.get("model").getAsString());

        return new Cosmetic(
                id,
                name,
                texture,
                new CosmeticModel(model, texture),
                CosmeticAnchor.valueOf(json.get("anchor").getAsString())
        );
    }
}
