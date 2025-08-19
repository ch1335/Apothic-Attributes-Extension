package com.chen1335.apothicAttributesExtension.mixinHooks;

import com.chen1335.apothicAttributesExtension.common.FishingTimeReductionToAttribute;
import com.chen1335.apothicAttributesExtension.common.LuckOfTheSeaToAttribute;
import com.google.gson.JsonElement;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public class RegistryDataLoaderHooks {
    public static <E> void onLoad(WritableRegistry<E> registry, ResourceKey<E> resourceKey, JsonElement jsonelement) {
        if (registry.key().equals(Registries.ENCHANTMENT)) {
            FishingTimeReductionToAttribute.process(resourceKey, jsonelement);
            LuckOfTheSeaToAttribute.process(resourceKey, jsonelement);
        }
    }
}
