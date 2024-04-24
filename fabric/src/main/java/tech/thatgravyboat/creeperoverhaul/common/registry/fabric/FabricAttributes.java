package tech.thatgravyboat.creeperoverhaul.common.registry.fabric;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import tech.thatgravyboat.creeperoverhaul.Creepers;

public class FabricAttributes {

    public static final Attribute REACH_DISTANCE = new RangedAttribute("generic.reach_distance", 5.0D, 0.0D, 1024.0D).setSyncable(true);
    public static final Attribute SWIM_SPEED = new RangedAttribute("generic.swim_speed", 1.0D, 0.0D, 1024.0D).setSyncable(true);

    public static void register() {
        Registry.register(BuiltInRegistries.ATTRIBUTE, new ResourceLocation(Creepers.MODID, "reach_distance"), REACH_DISTANCE);
        Registry.register(BuiltInRegistries.ATTRIBUTE, new ResourceLocation(Creepers.MODID, "swim_speed"), SWIM_SPEED);
    }
}
