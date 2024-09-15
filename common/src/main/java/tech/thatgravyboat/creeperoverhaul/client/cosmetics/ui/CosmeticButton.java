package tech.thatgravyboat.creeperoverhaul.client.cosmetics.ui;

import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import com.teamresourceful.resourcefulconfig.client.components.ModSprites;
import com.teamresourceful.resourcefullib.client.CloseablePoseStack;
import com.teamresourceful.resourcefullib.client.utils.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.Cosmetic;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.CosmeticModel;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.service.CosmeticsApi;
import tech.thatgravyboat.creeperoverhaul.client.renderer.cosmetics.CosmeticRenderer;

public class CosmeticButton extends AbstractButton {

    private final CosmeticRenderer renderer = new CosmeticRenderer();
    private final Cosmetic cosmetic;

    public CosmeticButton(Cosmetic cosmetic) {
        super(0, 0, 35, 35, CommonComponents.EMPTY);
        this.cosmetic = cosmetic;
        this.setTooltip(Tooltip.create(Component.literal(cosmetic.name())));
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        ResourceLocation texture = this.isHovered ? ModSprites.BUTTON_HOVER : ModSprites.BUTTON;
        graphics.blitSprite(texture, getX(), getY(), getWidth(), getHeight());
        try (
            var ignored = RenderUtils.createScissor(Minecraft.getInstance(), graphics, getX(), getY(), getWidth(), getHeight());
            var stack = new CloseablePoseStack(graphics)
        ) {
            stack.translate(getX() + 30, getY() + 30, 1000);
            stack.mulPose(Axis.XN.rotationDegrees(180));
            stack.mulPose(Axis.YN.rotationDegrees(180));
            stack.scale(25, 25, 25);
            cosmetic.displayTransformation().applyScale(stack);

            stack.translate(0.05, -0.4, 0);

            stack.mulPose(Axis.YP.rotationDegrees(135));
            stack.translate(0, 0, 0.6);
            cosmetic.displayTransformation().applyRotation(stack);
            cosmetic.displayTransformation().applyTranslation(stack);

            MultiBufferSource.BufferSource source = Minecraft.getInstance().renderBuffers().bufferSource();

            RenderType type = RenderType.entityTranslucent(cosmetic.texture().getResourceLocation());
            VertexConsumer consumer = source.getBuffer(type);

            CosmeticModel model = cosmetic.model();
            BakedGeoModel bakedModel = model.getBakedModel(null);
            if (model.isLoaded()) {
                renderer.setModel(model);

                AnimationState<Cosmetic> state = new AnimationState<>(
                        cosmetic, 0, 0, partialTicks, true);
                model.setCustomAnimations(cosmetic, renderer.getInstanceId(cosmetic), state);

                renderer.reRender(
                        bakedModel, stack, source, cosmetic,
                        type, consumer,
                        partialTicks,
                        LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, 0xFFFFFFFF
                );

                source.endBatch();
            }
        }
    }

    @Override
    public void onPress() {
        CosmeticsApi.setCosmetic(this.cosmetic);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
