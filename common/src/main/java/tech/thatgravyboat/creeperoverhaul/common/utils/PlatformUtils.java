package tech.thatgravyboat.creeperoverhaul.common.utils;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

import java.util.function.Supplier;

public class PlatformUtils {

    @ExpectPlatform
    public static boolean shouldHidePowerLayer() {
        return false;
    }

    @ExpectPlatform
    public static Level.ExplosionInteraction getInteractionForCreeper(BaseCreeper creeper) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static String formatShaderId(ResourceLocation location) {
        throw new AssertionError();
    }

    @Contract(pure = true)
    @ExpectPlatform
    public static boolean isShears(ItemStack stack) {
        throw new AssertionError();
    }

    @Contract(pure = true)
    @ExpectPlatform
    public static boolean isFlintAndSteel(ItemStack stack) {
        throw new AssertionError();
    }

    @Nullable
    @ExpectPlatform
    public static Attribute getModAttribute(String name) {
        throw new AssertionError();
    }
}
