package com.chen1335.apothicAttributesExtension.mixinHooks;

import com.google.gson.JsonElement;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;

public class RegistryDataLoaderHooks {
    public static <E> void onLoad(WritableRegistry<E> registry, ResourceKey<E> resourceKey, JsonElement jsonelement) {
        if (registry.key().equals(Registries.ENCHANTMENT)) {

        }
    }
}
