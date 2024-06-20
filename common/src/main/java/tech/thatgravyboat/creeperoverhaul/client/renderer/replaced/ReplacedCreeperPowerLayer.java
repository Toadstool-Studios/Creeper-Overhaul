package tech.thatgravyboat.creeperoverhaul.client.renderer.replaced;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Creeper;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;
import tech.thatgravyboat.creeperoverhaul.common.entity.ReplacedCreeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

public class ReplacedCreeperPowerLayer extends GeoRenderLayer<ReplacedCreeper> {

    private static final ResourceLocation PLAINS_CHARGED_TEXTURE = Creepers.id("textures/entity/armor/creeper_armor.png");

    private final ReplacedCreeperRenderer renderer;

    public ReplacedCreeperPowerLayer(ReplacedCreeperRenderer renderer) {
        super(renderer);
        this.renderer = renderer;
    }


    @Override
    public void render(PoseStack poseStack, ReplacedCreeper animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        if(PlatformUtils.shouldHidePowerLayer()) return;
        Creeper creeper = this.renderer.getCurrentEntity();
        if (creeper.isPowered()) {

            float f = (float)creeper.tickCount + partialTick;

            RenderType type = RenderTypes.getSwirl(PLAINS_CHARGED_TEXTURE, f * 0.005F % 1F,  f * 0.005F % 1F);
            VertexConsumer armorConsumer = bufferSource.getBuffer(type);

            getRenderer().reRender(
                    getDefaultBakedModel(animatable), poseStack,
                    bufferSource, animatable,
                    type, armorConsumer,
                    partialTick,
                    packedLight, OverlayTexture.NO_OVERLAY,
                    0xFFFFFFFF
            );
        }
    }
}
