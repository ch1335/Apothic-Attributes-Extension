package com.chen1335.apothicAttributesExtension.data;

import com.chen1335.apothicAttributesExtension.API.AttributeTags;
import com.chen1335.apothicAttributesExtension.API.objects.ModAttributes;
import com.chen1335.apothicAttributesExtension.ApothicAttributesExtension;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.ResourceLocation;
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

        IntrinsicTagAppender<Attribute> tagAppender = tag(AttributeTags.ATTRIBUTE_SORT);
        tagAppender.add(
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

        addOptionals(tagAppender,
                AttributeRegistry.MAX_MANA.getId(),
                AttributeRegistry.SPELL_POWER.getId()
        );


    }

    private void addOptionals(IntrinsicTagAppender<Attribute> tagAppender, ResourceLocation... resourceLocations) {
        for (ResourceLocation location : resourceLocations) {
            tagAppender.addOptional(location);
        }
    }
}
