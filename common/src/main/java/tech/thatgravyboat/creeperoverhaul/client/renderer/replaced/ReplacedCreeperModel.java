package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.Events;

public class ReplacedCreeperModel<E extends ReplacedCreeper> extends GeoModel<E> {

    private static final ResourceLocation MODEL = new ResourceLocation(Creepers.MODID, "geo/plains.geo.json");
    private static final ResourceLocation APRIL_MODEL = new ResourceLocation(Creepers.MODID, "geo/april_fools_creeper.geo.json");
    private static final ResourceLocation ANIMATION = new ResourceLocation(Creepers.MODID, "animations/creeper.animation.json");

    private static final ResourceLocation NORMAL = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper.png");
    private static final ResourceLocation CHRISTMAS = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper_christmas.png");
    private static final ResourceLocation HALLOWEEN = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper_halloween.png");
    private static final ResourceLocation ST_PATRICKS = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper_stpatricksday.png");
    private static final ResourceLocation APRIL_FOOLS = new ResourceLocation(Creepers.MODID, "textures/entity/plains/plains_creeper_aprilfools.png");

    @Override
    public ResourceLocation getModelResource(ReplacedCreeper object) {
        if (Creepers.EVENT == Events.APRIL_FOOLS) {
            return APRIL_MODEL;
        }
        return MODEL;
    }

    @Override
    public ResourceLocation getTextureResource(ReplacedCreeper object) {
        return switch (Creepers.EVENT) {
            case CHRISTMAS -> CHRISTMAS;
            case HALLOWEEN -> HALLOWEEN;
            case ST_PATRICKS_DAY -> ST_PATRICKS;
            case APRIL_FOOLS -> APRIL_FOOLS;
            case NONE -> NORMAL;
        };
    }

    @Override
    public ResourceLocation getAnimationResource(ReplacedCreeper animatable) {
        return ANIMATION;
    }
}
