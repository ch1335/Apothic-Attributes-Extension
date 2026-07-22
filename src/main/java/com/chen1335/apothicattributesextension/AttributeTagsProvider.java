package com.chen1335.apothicattributesextension;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AttributeTagsProvider extends IntrinsicHolderTagsProvider<Attribute> {
    public AttributeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(
            output,
            Registries.ATTRIBUTE,
            lookupProvider,
            attribute -> BuiltInRegistries.ATTRIBUTE.getResourceKey(attribute).orElseThrow(),
            ApothicAttributesExtension.MODID
        );
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        var attributes = tag(AttributeTags.ATTRIBUTE_SORT);
        attributes.add(
            Attributes.ATTACK_DAMAGE.value(),
            Attributes.ATTACK_SPEED.value(),
            Attributes.MAX_HEALTH.value(),
            Attributes.ARMOR.value(),
            Attributes.ARMOR_TOUGHNESS.value(),
            Attributes.MOVEMENT_SPEED.value(),
            ModAttributes.MOB_LOOTING.get(),
            ModAttributes.MINING_FORTUNE.get(),
            ModAttributes.FISHING_SPEED.get(),
            ModAttributes.FISHING_LUCK.get()
        );
        getOrCreateRawBuilder(AttributeTags.ATTRIBUTE_SORT)
            .addOptionalElement(net.minecraft.resources.Identifier.fromNamespaceAndPath("irons_spellbooks", "max_mana"))
            .addOptionalElement(net.minecraft.resources.Identifier.fromNamespaceAndPath("irons_spellbooks", "spell_power"));
    }
}
