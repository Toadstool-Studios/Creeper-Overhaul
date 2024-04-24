package tech.thatgravyboat.creeperoverhaul.mixin.fabric;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Shadow
    @Final
    private Map<String, ShaderInstance> shaders;

    @Inject(
        method = "reloadShaders",
        at = @At("TAIL")
    )
    private void creeperoverhaul$onShadersReload(ResourceProvider resourceProvider, CallbackInfo ci) {
        List<Pair<ShaderInstance, Consumer<ShaderInstance>>> loadingShaders = new ArrayList<>();

        try {
            loadingShaders.add(RenderTypes.registerShader(resourceProvider));
        } catch (Exception e) {
            loadingShaders.forEach(pair -> pair.getFirst().close());
            throw new RuntimeException("[Creeper Overhaul] Shaders could not be reloaded", e);
        }

        loadingShaders.forEach(pair -> {
            ShaderInstance shader = pair.getFirst();
            this.shaders.put(shader.getName(), shader);
            pair.getSecond().accept(shader);
        });
    }
}
