package tech.thatgravyboat.creeperoverhaul.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.common.block.TinyCactusBlock;

import java.util.function.Supplier;

public class ModBlocks {

    public static final ResourcefulRegistry<Block> BLOCKS = ResourcefulRegistries.create(BuiltInRegistries.BLOCK, Creepers.MODID);

    public static final RegistryEntry<Block> TINY_CACTUS = BLOCKS.register("tiny_cactus", TinyCactusBlock::new);
    public static final RegistryEntry<Block> POTTED_TINY_CACTUS = BLOCKS.register("potted_tiny_cactus", () -> new FlowerPotBlock(TINY_CACTUS.get(), BlockBehaviour.Properties.copy(Blocks.POTTED_CACTUS)));
}
