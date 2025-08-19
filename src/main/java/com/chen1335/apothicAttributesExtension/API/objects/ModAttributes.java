package com.chen1335.apothicAttributesExtension.API.objects;

import com.chen1335.apothicAttributesExtension.ApothicAttributesExtension;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTE_DEFERRED_REGISTER = DeferredRegister.create(Registries.ATTRIBUTE, ApothicAttributesExtension.MODID);


    public static final DeferredHolder<Attribute, Attribute> MINING_FORTUNE = ATTRIBUTE_DEFERRED_REGISTER.register("mining_fortune", () -> new RangedAttribute(makeDescriptionId("mining_fortune"), 0, 0, 114514).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> MOB_LOOTING = ATTRIBUTE_DEFERRED_REGISTER.register("mob_looting", () -> new RangedAttribute(makeDescriptionId("mob_looting"), 0, 0, 114514).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> FISHING_SPEED = ATTRIBUTE_DEFERRED_REGISTER.register("fishing_speed", () -> new RangedAttribute(makeDescriptionId("fishing_speed"), 0, 0, 114514).setSyncable(true));

    public static final DeferredHolder<Attribute, Attribute> FISHING_LUCK = ATTRIBUTE_DEFERRED_REGISTER.register("fishing_luck", () -> new RangedAttribute(makeDescriptionId("fishing_luck"), 0, 0, 114514).setSyncable(true));

    private static String makeDescriptionId(String s) {
        return ApothicAttributesExtension.MODID + ".attribute.name.generic." + s;
    }
}
