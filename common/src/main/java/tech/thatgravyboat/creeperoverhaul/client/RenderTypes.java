package tech.thatgravyboat.creeperoverhaul.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.util.function.Consumer;

public class RenderTypes extends RenderType {

    private static ShaderInstance energySwirlShader;
    public static final ResourceLocation ENERGY_SWIRL_RENDERTYPE = Creepers.id("rendertype_energy_swirl");
    private static final ShaderStateShard ENERGY_SWIRL_SHARD = new ShaderStateShard(() -> energySwirlShader);

    public RenderTypes(String string, VertexFormat vertexFormat, VertexFormat.Mode mode, int i, boolean bl, boolean bl2, Runnable runnable, Runnable runnable2) {
        super(string, vertexFormat, mode, i, bl, bl2, runnable, runnable2);
    }

    public static void registerShaders(Registrar registrar) {
        registrar.register(ENERGY_SWIRL_RENDERTYPE, DefaultVertexFormat.NEW_ENTITY, RenderTypes::setEnergyShader);
    }

    public static void setEnergyShader(ShaderInstance shader) {
        RenderTypes.energySwirlShader = shader;
    }

    public static RenderType getSwirl(ResourceLocation location, float u, float v) {
        return create(ENERGY_SWIRL_RENDERTYPE.toString(),
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                false,
                true,
                RenderType.CompositeState.builder()
                        .setShaderState(ENERGY_SWIRL_SHARD)
                        .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                        .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(u, v))
                        .setTransparencyState(ADDITIVE_TRANSPARENCY)
                        .setCullState(NO_CULL)
                        .setLightmapState(LIGHTMAP)
                        .setOverlayState(OVERLAY)
                        .createCompositeState(false)
        );
    }

    public static RenderType getTransparentEyes(ResourceLocation location) {
        return create("eyes",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS, 256,
                false, true,
                RenderType.CompositeState.builder()
                        .setShaderState(RENDERTYPE_EYES_SHADER)
                        .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                        .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
                        .setWriteMaskState(COLOR_WRITE)
                        .createCompositeState(false)
        );
    }

    public interface Registrar {
        void register(ResourceLocation id, VertexFormat vertexFormat, Consumer<ShaderInstance> loadCallback);
    }
}
