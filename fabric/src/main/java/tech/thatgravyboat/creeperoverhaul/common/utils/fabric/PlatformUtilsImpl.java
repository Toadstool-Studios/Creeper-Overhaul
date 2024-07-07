package tech.thatgravyboat.creeperoverhaul.common.utils.fabric;

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.core.Holder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import tech.thatgravyboat.creeperoverhaul.common.config.CreepersConfig;
import tech.thatgravyboat.creeperoverhaul.common.entity.base.BaseCreeper;
import tech.thatgravyboat.creeperoverhaul.common.registry.fabric.FabricAttributes;

public class PlatformUtilsImpl {
    public static boolean shouldHidePowerLayer() {
        return false;
    }

    public static Level.ExplosionInteraction getInteractionForCreeper(BaseCreeper creeper) {
        boolean destroyBlocks = creeper.level().getGameRules().getRule(GameRules.RULE_MOBGRIEFING).get() && CreepersConfig.destroyBlocks;
        return destroyBlocks ? Level.ExplosionInteraction.MOB : Level.ExplosionInteraction.NONE;
    }

    public static boolean isShears(ItemStack stack) {
        return stack.getItem() instanceof ShearsItem || stack.is(ConventionalItemTags.SHEAR_TOOLS);
    }

    public static boolean isFlintAndSteel(ItemStack stack) {
        return stack.getItem() instanceof FlintAndSteelItem || stack.is(ItemTags.CREEPER_IGNITERS);
    }

    public static Holder<Attribute> getModAttribute(String name) {
        if ("swim_speed".equals(name)) {
            return FabricAttributes.getSwimSpeed();
        }
        return null;
    }
}
