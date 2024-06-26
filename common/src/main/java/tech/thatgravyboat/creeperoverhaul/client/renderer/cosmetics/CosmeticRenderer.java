package tech.thatgravyboat.creeperoverhaul.client.renderer.cosmetics;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoObjectRenderer;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.Cosmetic;

public class CosmeticRenderer extends GeoObjectRenderer<Cosmetic> {

    private GeoModel<Cosmetic> model;

    public CosmeticRenderer() {
        super(null);
    }

    @Override
    public GeoModel<Cosmetic> getGeoModel() {
        return this.model;
    }

    public void setModel(GeoModel<Cosmetic> model) {
        this.model = model;
    }

    @Override
    public void actuallyRender(PoseStack poseStack, Cosmetic cosmetic, BakedGeoModel model, @Nullable RenderType renderType, MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        if (getGeoModel() == null) return;
        poseStack.pushPose();

        if (!isReRender) {
            AnimationState<Cosmetic> animationState = new AnimationState<>(cosmetic, 0, 0, partialTick, false);
            long instanceId = getInstanceId(cosmetic);

            getGeoModel().addAdditionalStateData(cosmetic, instanceId, animationState::setData);
            getGeoModel().handleAnimations(cosmetic, instanceId, animationState, partialTick);
        }

        this.modelRenderTranslations = new Matrix4f(poseStack.last().pose());

        updateAnimatedTextureFrame(cosmetic);
        for (GeoBone group : model.topLevelBones()) {
            renderRecursively(
                    poseStack, cosmetic, group, renderType,
                    bufferSource, buffer, isReRender, partialTick, packedLight,
                    packedOverlay, colour
            );
        }
        poseStack.popPose();
    }
}
