package com.chen1335.apothicattributesextension;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, ApothicAttributesExtension.MODID);

    public static final DeferredHolder<Attribute, Attribute> MINING_FORTUNE = register("mining_fortune");
    public static final DeferredHolder<Attribute, Attribute> MOB_LOOTING = register("mob_looting");
    public static final DeferredHolder<Attribute, Attribute> FISHING_SPEED = register("fishing_speed");
    public static final DeferredHolder<Attribute, Attribute> FISHING_LUCK = register("fishing_luck");

    private ModAttributes() {
    }

    private static DeferredHolder<Attribute, Attribute> register(String name) {
        return ATTRIBUTES.register(name, () -> new RangedAttribute(descriptionId(name), 0.0, 0.0, 114514.0).setSyncable(true));
    }

    private static String descriptionId(String name) {
        return ApothicAttributesExtension.MODID + ".attribute.name.generic." + name;
    }
}
