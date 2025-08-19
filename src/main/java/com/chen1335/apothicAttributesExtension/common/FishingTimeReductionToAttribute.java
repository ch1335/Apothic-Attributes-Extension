package com.chen1335.apothicAttributesExtension.common;

import com.chen1335.apothicAttributesExtension.API.objects.ModAttributes;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FishingTimeReductionToAttribute {
    public static <E> void process(ResourceKey<E> resourceKey, JsonElement jsonelement) {
        Pair<List<ConditionalEffect<EnchantmentValueEffect>>, JsonElement> listJsonElementPair = null;
        JsonObject jsonObject = jsonelement.getAsJsonObject();
        if (jsonObject.has("effects")) {
            JsonObject effects = jsonObject.get("effects").getAsJsonObject();
            if (effects.has("minecraft:fishing_time_reduction")) {
                listJsonElementPair = Objects.requireNonNull(EnchantmentEffectComponents.FISHING_TIME_REDUCTION.codec()).decode(JsonOps.INSTANCE, effects.getAsJsonArray("minecraft:fishing_time_reduction")).getOrThrow();
            }
        }

        if (listJsonElementPair != null) {
            jsonObject.get("effects").getAsJsonObject().remove("minecraft:fishing_time_reduction");
            List<EnchantmentAttributeEffect> effects = new ArrayList<>();
            for (ConditionalEffect<EnchantmentValueEffect> enchantmentValueEffectConditionalEffect : listJsonElementPair.getFirst()) {
                if (enchantmentValueEffectConditionalEffect.effect() instanceof AddValue addValue && addValue.value() instanceof LevelBasedValue.Linear linear) {
                    effects.add(new EnchantmentAttributeEffect(
                            ResourceLocation.withDefaultNamespace("enchantment.fishing_speed"),
                            ModAttributes.FISHING_SPEED,
                            LevelBasedValue.perLevel(linear.base() * 5, linear.perLevelAboveFirst() * 5),
                            AttributeModifier.Operation.ADD_VALUE
                    ));
                }
            }


            JsonElement jsonElement = Objects.requireNonNull(EnchantmentEffectComponents.ATTRIBUTES.codec()).encodeStart(JsonOps.INSTANCE, effects).getOrThrow();
            jsonObject.get("effects").getAsJsonObject().add("minecraft:attributes", jsonElement);
        }
    }
}
