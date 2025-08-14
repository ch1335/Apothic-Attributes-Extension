package com.chen1335.apothicAttributesExtension.API;

import com.chen1335.apothicAttributesExtension.ApothicAttributesExtension;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;

public interface ExtensionDamageTags {
    TagKey<DamageType> CAN_CRITICAL = TagKey.create(Registries.DAMAGE_TYPE, ApothicAttributesExtension.id("can_critical"));

    TagKey<DamageType> CAN_LIFE_STEAL = TagKey.create(Registries.DAMAGE_TYPE, ApothicAttributesExtension.id("can_life_steal"));
}
