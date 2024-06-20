package tech.thatgravyboat.creeperoverhaul.common.registry.fabric;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import tech.thatgravyboat.creeperoverhaul.Creepers;

public class FabricAttributes {

    private static Holder<Attribute> swimSpeed;

    public static void register() {
        swimSpeed = Registry.registerForHolder(
                BuiltInRegistries.ATTRIBUTE,
                Creepers.id("swim_speed"),
                new RangedAttribute("generic.swim_speed", 1.0D, 0.0D, 1024.0D).setSyncable(true)
        );
    }

    public static Holder<Attribute> getSwimSpeed() {
        return swimSpeed;
    }
}
