package tech.thatgravyboat.creeperoverhaul.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforged.fml.event.lifecycle.InterModProcessEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.api.CreeperPlugin;
import tech.thatgravyboat.creeperoverhaul.api.PluginRegistry;
import tech.thatgravyboat.creeperoverhaul.client.neoforge.CreepersForgeClient;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModBlocks;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSpawns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod(Creepers.MODID)
public class CreepersForge {

    public CreepersForge(IEventBus bus) {
        Creepers.init();
        bus.addListener(this::onAttributes);
        bus.addListener(this::onComplete);
        bus.addListener(this::onCommonSetup);
        bus.addListener(CreepersForgeClient::onShaderRegister);
        bus.addListener(CreepersForgeClient::onClient);
        bus.addListener(this::onIMC);
    }

    public void onAttributes(EntityAttributeCreationEvent event) {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes = new HashMap<>();
        Creepers.registerAttributes(attributes);
        for (var entry : attributes.entrySet()) {
            event.put(entry.getKey(), entry.getValue().build());
        }
    }

    public void onComplete(FMLLoadCompleteEvent event) {
        ModSpawns.addSpawnRules();
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(new ResourceLocation(Creepers.MODID, "tiny_cactus"), ModBlocks.POTTED_TINY_CACTUS);
    }

    public void onIMC(InterModProcessEvent event) {
        List<CreeperPlugin> plugins = event.getIMCStream(s -> s.equalsIgnoreCase("plugin/register"))
                .map(InterModComms.IMCMessage::messageSupplier)
                .map(Supplier::get)
                .filter(message -> message instanceof CreeperPlugin)
                .map(message -> (CreeperPlugin) message)
                .toList();
        PluginRegistry.getInstance().registerPlugins(plugins);
    }
}
