package com.chen1335.apothicattributesextension;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;

public final class AttributeTags {
    public static final TagKey<Attribute> ATTRIBUTE_SORT = TagKey.create(Registries.ATTRIBUTE, ApothicAttributesExtension.id("attribute_sort"));

    private AttributeTags() {
    }
}
