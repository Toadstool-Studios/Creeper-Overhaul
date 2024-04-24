package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

public class CreeperModel<E extends BaseCreeper> extends GeoModel<E> {

    private final CreeperType type;

    public CreeperModel(CreeperType type) {
        this.type = type;
    }

    @Override
    public ResourceLocation getModelResource(E entity) {
        return type.isShearable() && entity.isSheared() && type.shearedModel() != null ? type.shearedModel().apply(entity) : type.model().apply(entity);
    }

    @Override
    public ResourceLocation getTextureResource(E entity) {
        return type.texture().apply(entity);
    }

    @Override
    public ResourceLocation getAnimationResource(E entity) {
        return type.animation().apply(entity);
    }

    @Override
    public void setCustomAnimations(E animatable, long instanceId, AnimationState<E> state) {
        super.setCustomAnimations(animatable, instanceId, state);

        EntityModelData extraDataOfType = state.getData(DataTickets.ENTITY_MODEL_DATA);
        var head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            head.setRotY(extraDataOfType.netHeadYaw() * ((float)Math.PI / 180F));
        }
    }
}
