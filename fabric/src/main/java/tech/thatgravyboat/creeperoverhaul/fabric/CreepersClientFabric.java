package tech.thatgravyboat.creeperoverhaul.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import tech.thatgravyboat.creeperoverhaul.client.CreepersClient;
import tech.thatgravyboat.creeperoverhaul.client.RenderTypes;

public class CreepersClientFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        CreepersClient.init();
        CreepersClient.registerRenderers();

        CoreShaderRegistrationCallback.EVENT.register((registrationCallback) ->
            RenderTypes.registerShaders((id, format, callback) -> {
                try {
                    registrationCallback.register(id, format, callback);
                } catch (Exception e) {
                    throw new RuntimeException("[Creeper Overhaul] Shaders could not be reloaded", e);
                }
            })
        );
    }
}
