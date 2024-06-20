package tech.thatgravyboat.creeperoverhaul.common.entity.base;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import tech.thatgravyboat.creeperoverhaul.common.utils.PlatformUtils;

public class CreeperAttributeBuilder {

    private final AttributeSupplier.Builder builder = Creeper.createAttributes()
            .add(Attributes.ENTITY_INTERACTION_RANGE, 0)
            .add(PlatformUtils.getModAttribute("swim_speed"));

    public CreeperAttributeBuilder add(Holder<Attribute> attribute, double amount) {
        builder.add(attribute, amount);
        return this;
    }

    public CreeperAttributeBuilder add(String attribute, double value) {
        Holder<Attribute> modAttribute = PlatformUtils.getModAttribute(attribute);
        if (modAttribute == null) {
            throw new IllegalArgumentException("Mod Attribute " + attribute + " does not exist");
        }
        return add(modAttribute, value);
    }

    public AttributeSupplier.Builder build() {
        return builder;
    }
}
