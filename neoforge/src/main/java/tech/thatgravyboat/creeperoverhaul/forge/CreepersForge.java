package tech.thatgravyboat.creeperoverhaul.forge;

import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModProcessEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.SpawnPlacementRegisterEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.api.CreeperPlugin;
import tech.thatgravyboat.creeperoverhaul.api.PluginRegistry;
import tech.thatgravyboat.creeperoverhaul.client.neoforge.CreepersForgeClient;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModBlocks;
import tech.thatgravyboat.creeperoverhaul.common.registry.ModSpawns;
import tech.thatgravyboat.creeperoverhaul.common.utils.ServerCosmetics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mod(Creepers.MODID)
public class CreepersForge {

    public CreepersForge(IEventBus bus) {
        Creepers.init();
        bus.addListener(this::onAttributes);
        bus.addListener(this::onSpawnPlacement);
        bus.addListener(this::onCommonSetup);
        bus.addListener(CreepersForgeClient::onShaderRegister);
        bus.addListener(CreepersForgeClient::onClient);
        bus.addListener(this::onIMC);

        if (FMLLoader.getDist().isClient()) {
            CreepersForgeClient.init(bus);
        }
    }

    public void onAttributes(EntityAttributeCreationEvent event) {
        Map<EntityType<? extends LivingEntity>, AttributeSupplier.Builder> attributes = new HashMap<>();
        Creepers.registerAttributes(attributes);
        for (var entry : attributes.entrySet()) {
            event.put(entry.getKey(), entry.getValue().build());
        }
    }

    public void onSpawnPlacement(SpawnPlacementRegisterEvent event) {
        ModSpawns.addSpawnRules(new ModSpawns.Registrar() {
            @Override
            public <T extends Mob> void register(Supplier<EntityType<T>> entityType, SpawnPlacementType type, Heightmap.Types types, SpawnPlacements.SpawnPredicate<T> spawnPredicate) {
                event.register(entityType.get(), type, types, spawnPredicate, SpawnPlacementRegisterEvent.Operation.OR);
            }
        });
    }

    public void onCommonSetup(FMLCommonSetupEvent event) {
        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(Creepers.id("tiny_cactus"), ModBlocks.POTTED_TINY_CACTUS);
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
