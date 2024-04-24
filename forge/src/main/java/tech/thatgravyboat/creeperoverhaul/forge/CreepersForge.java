package tech.thatgravyboat.creeperoverhaul.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.api.CreeperPlugin;
import tech.thatgravyboat.creeperoverhaul.api.PluginRegistry;
import tech.thatgravyboat.creeperoverhaul.client.forge.CreepersForgeClient;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModBlocks;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSpawns;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod(Creepers.MODID)
public class CreepersForge {

    public CreepersForge() {
        Creepers.init();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

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
