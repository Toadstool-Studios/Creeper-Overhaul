package tech.thatgravyboat.creeperoverhaul.common.utils.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.event.ForgeEventFactory;
import tech.thatgravyboat.creeperoverhaul.common.config.CreepersConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

public class PlatformUtilsImpl {

    private static final ToolAction SHEARS_ENTITY_USE = ToolAction.get("shears_entity_use");
    private static final ToolAction IGNITE = ToolAction.get("ignite");

    private static Boolean usingOptifine = null;

    public static boolean shouldHidePowerLayer() {
        if (usingOptifine == null) {
            try {
                Class.forName("optifine.Installer");
                usingOptifine = true;
            }catch (Exception ignored) {
                usingOptifine = false;
            }
        }
        return usingOptifine;
    }

    public static Level.ExplosionInteraction getInteractionForCreeper(BaseCreeper creeper) {
        boolean destroyBlocks = ForgeEventFactory.getMobGriefingEvent(creeper.level(), creeper) && CreepersConfig.destroyBlocks;
        return destroyBlocks ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE;
    }

    public static String formatShaderId(ResourceLocation location) {
        return location.toString();
    }

    public static boolean isShears(ItemStack stack) {
        return stack.getItem() instanceof ShearsItem || stack.canPerformAction(SHEARS_ENTITY_USE);
    }

    public static boolean isFlintAndSteel(ItemStack stack) {
        return stack.getItem() instanceof FlintAndSteelItem || stack.canPerformAction(IGNITE);
    }

    public static Attribute getModAttribute(String name) {
        return switch (name) {
            case "swim_speed" -> ForgeMod.SWIM_SPEED.get();
            case "reach_distance" -> ForgeMod.ENTITY_REACH.get();
            default -> null;
        };
    }
}
