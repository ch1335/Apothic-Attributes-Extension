package com.chen1335.apothicAttributesExtension.data;

import com.chen1335.apothicAttributesExtension.API.AttributeTags;
import com.chen1335.apothicAttributesExtension.ApothicAttributesExtension;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class AttributeTagsProvider extends IntrinsicHolderTagsProvider<Attribute> {


    public AttributeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ATTRIBUTE, lookupProvider, attribute -> BuiltInRegistries.ATTRIBUTE.getResourceKey(attribute).get(), ApothicAttributesExtension.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(AttributeTags.ATTRIBUTE_SORT).add(
                Attributes.ATTACK_DAMAGE.value(),
                Attributes.ATTACK_SPEED.value(),
                Attributes.MAX_HEALTH.value(),
                Attributes.ARMOR.value(),
                Attributes.ARMOR_TOUGHNESS.value(),
                Attributes.MOVEMENT_SPEED.value()
        );
    }

}
