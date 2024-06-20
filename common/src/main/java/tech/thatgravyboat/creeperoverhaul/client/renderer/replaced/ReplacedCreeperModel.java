package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.Events;

import java.util.Locale;

public class ReplacedCreeperModel<E extends ReplacedCreeper> extends GeoModel<E> {

    private static final ResourceLocation ANIMATION = Creepers.id("animations/creeper.animation.json");

    @Override
    public ResourceLocation getModelResource(ReplacedCreeper object) {
        if (Creepers.EVENT == Events.NONE) {
            return Creepers.id("geo/plains.geo.json");
        } else {
            String id = Creepers.EVENT.name().toLowerCase(Locale.ROOT);
            return Creepers.id("geo/events/" + id + ".geo.json");
        }
    }

    @Override
    public ResourceLocation getTextureResource(ReplacedCreeper object) {
        if (Creepers.EVENT == Events.NONE) {
            return Creepers.id("textures/entity/plains/plains_creeper.png");
        } else {
            String id = Creepers.EVENT.name().toLowerCase(Locale.ROOT);
            return Creepers.id("textures/entity/events/" + id + ".png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(ReplacedCreeper animatable) {
        return ANIMATION;
    }
}
