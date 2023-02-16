package tech.thatgravyboat.creeperoverhaul.client.renderer.normal;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.CreeperType;

import java.util.List;

public class CreeperModel<E extends BaseCreeper> extends AnimatedGeoModel<E> {

    private final CreeperType type;

    public CreeperModel(CreeperType type) {
        this.type = type;
    }

    @Override
    public Identifier getModelLocation(E entity) {
        return type.shearable() && entity.isSheared() && type.shearedModel() != null ? type.shearedModel() : type.model();
    }

    @Override
    public Identifier getTextureLocation(E entity) {
        return type.texture();
    }

    @Override
    public Identifier getAnimationFileLocation(E entity) {
        return type.animation();
    }

    @Override
    public void setCustomAnimations(E entity, int uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setCustomAnimations(entity, uniqueID, customPredicate);

        if (customPredicate == null) return;
        List<EntityModelData> extraDataOfType = ((AnimationEvent<?>)customPredicate)
                .getExtraDataOfType(EntityModelData.class);
        var head = this.getAnimationProcessor().getBone("head");
        if (head != null) {
            head.setRotationY(extraDataOfType.get(0).netHeadYaw * ((float)Math.PI / 180F));
        }
    }
}
