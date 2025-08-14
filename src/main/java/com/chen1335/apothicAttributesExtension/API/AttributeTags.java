package com.chen1335.apothicAttributesExtension.API;

import com.chen1335.apothicAttributesExtension.ApothicAttributesExtension;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;

public interface AttributeTags {
    TagKey<Attribute> ATTRIBUTE_SORT = TagKey.create(Registries.ATTRIBUTE, ResourceLocation.fromNamespaceAndPath(ApothicAttributesExtension.MODID, "attribute_sort"));
}
