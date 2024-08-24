package tech.thatgravyboat.creeperoverhaul.client.renderer.cosmetics;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.Cosmetic;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.CosmeticModel;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.Cosmetics;

public class CosmeticLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private final CosmeticRenderer renderer = new CosmeticRenderer();

    public CosmeticLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(
            PoseStack stack, MultiBufferSource source,
            int packedLight, AbstractClientPlayer entity,
            float limbSwing, float limbSwingAmount,
            float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch
    ) {
        Cosmetic cosmetic = Cosmetics.getCosmetic(entity.getUUID());
        if (cosmetic == null || entity.isInvisible()) return;

        stack.pushPose();

        ModelPart part = switch (cosmetic.anchor()) {
            case BODY -> getParentModel().body;
            case HEAD -> getParentModel().head;
            case LEFT_ARM -> getParentModel().leftArm;
            case RIGHT_ARM -> getParentModel().rightArm;
            case LEFT_LEG -> getParentModel().leftLeg;
            case RIGHT_LEG -> getParentModel().rightLeg;
        };
        part.translateAndRotate(stack);
        stack.mulPose(Axis.XN.rotationDegrees(180));
        stack.mulPose(Axis.YN.rotationDegrees(180));
        stack.translate(-0.5, -0.5, -0.5);

        RenderType type = RenderType.entityTranslucent(cosmetic.texture().getResourceLocation());
        VertexConsumer consumer = source.getBuffer(type);

        CosmeticModel model = cosmetic.model();
        BakedGeoModel bakedModel = model.getBakedModel(null);
        if (model.isLoaded()) {
            renderer.setModel(model);

            AnimationState<Cosmetic> state = new AnimationState<>(cosmetic, 0, 0, headPitch, true);
            model.setCustomAnimations(cosmetic, renderer.getInstanceId(cosmetic), state);

            renderer.reRender(
                    bakedModel, stack, source, cosmetic,
                    type, consumer,
                    partialTick,
                    packedLight, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF
            );
        }

        stack.popPose();
    }
}
