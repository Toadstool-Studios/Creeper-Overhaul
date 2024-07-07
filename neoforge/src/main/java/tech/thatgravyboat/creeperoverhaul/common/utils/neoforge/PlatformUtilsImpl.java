package tech.thatgravyboat.creeperoverhaul.common.utils.neoforge;

import net.minecraft.core.Holder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbility;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.EventHooks;
import tech.thatgravyboat.creeperoverhaul.common.config.CreepersConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;

public class PlatformUtilsImpl {

    private static final ItemAbility SHEARS_ENTITY_USE = ItemAbility.get("shears_entity_use");
    private static final ItemAbility IGNITE = ItemAbility.get("ignite");

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
        boolean destroyBlocks = EventHooks.canEntityGrief(creeper.level(), creeper) && CreepersConfig.destroyBlocks;
        return destroyBlocks ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE;
    }

    public static boolean isShears(ItemStack stack) {
        return stack.getItem() instanceof ShearsItem || stack.canPerformAction(SHEARS_ENTITY_USE) || stack.is(Tags.Items.TOOLS_SHEAR);
    }

    public static boolean isFlintAndSteel(ItemStack stack) {
        return stack.getItem() instanceof FlintAndSteelItem || stack.canPerformAction(IGNITE) || stack.is(ItemTags.CREEPER_IGNITERS);
    }

    public static Holder<Attribute> getModAttribute(String name) {
        if ("swim_speed".equals(name)) {
            return NeoForgeMod.SWIM_SPEED;
        }
        return null;
    }
}
