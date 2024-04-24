package tech.thatgravyboat.creeperoverhaul.client.forge;

import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import tech.thatgravyboat.creeperoverhaul.client.CreepersClient;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;

public class CreepersForgeClient {

    public static void onClient(FMLClientSetupEvent event) {
        CreepersClient.registerRenderers();
    }

    public static void onShaderRegister(RegisterShadersEvent event) {
        try {
            var shader = RenderTypes.registerShader(event.getResourceProvider());
            event.registerShader(shader.getFirst(), shader.getSecond());
        } catch (Exception e) {
            throw new RuntimeException("[Creeper Overhaul] Shaders could not be reloaded", e);
        }
    }

}
