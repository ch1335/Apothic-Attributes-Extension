package com.chen1335.apothicAttributesExtension.mixins.apothic_attributes_extension;

import com.chen1335.apothicAttributesExtension.mixinHooks.RegistryDataLoaderHooks;
import com.google.gson.JsonElement;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.WritableRegistry;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(RegistryDataLoader.class)
public class RegistryDataLoaderMixin {

    @ModifyArg(method = "loadElementFromResource", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Decoder;parse(Lcom/mojang/serialization/DynamicOps;Ljava/lang/Object;)Lcom/mojang/serialization/DataResult;"), index = 1)
    private static <T, E> T loadElementFromResource(T input, @Local(argsOnly = true) WritableRegistry<E> registry, @Local(argsOnly = true) ResourceKey<E> resourceKey) {
        RegistryDataLoaderHooks.onLoad(registry, resourceKey, (JsonElement) input);
        return input;
    }
}
