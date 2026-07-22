package com.chen1335.apothicattributesextension;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, ApothicAttributesExtension.MODID);

    public static final DeferredHolder<Attribute, Attribute> MINING_FORTUNE = register("mining_fortune",100);
    public static final DeferredHolder<Attribute, Attribute> MOB_LOOTING = register("mob_looting",100);
    public static final DeferredHolder<Attribute, Attribute> FISHING_SPEED = register("fishing_speed",350);
    public static final DeferredHolder<Attribute, Attribute> FISHING_LUCK = register("fishing_luck",100);

    private ModAttributes() {
    }

    private static DeferredHolder<Attribute, Attribute> register(String name,double max) {
        return ATTRIBUTES.register(name, () -> new RangedAttribute(descriptionId(name), 0.0, 0.0, max).setSyncable(true));
    }

    private static String descriptionId(String name) {
        return ApothicAttributesExtension.MODID + ".attribute.name.generic." + name;
    }
}
