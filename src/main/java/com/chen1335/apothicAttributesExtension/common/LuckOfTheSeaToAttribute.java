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

public class LuckOfTheSeaToAttribute {
    public static <E> void process(ResourceKey<E> resourceKey, JsonElement jsonelement) {
        Pair<List<ConditionalEffect<EnchantmentValueEffect>>, JsonElement> listJsonElementPair = null;
        JsonObject jsonObject = jsonelement.getAsJsonObject();
        if (jsonObject.has("effects")) {
            JsonObject effects = jsonObject.get("effects").getAsJsonObject();
            if (effects.has("minecraft:fishing_luck_bonus")) {
                listJsonElementPair = Objects.requireNonNull(EnchantmentEffectComponents.FISHING_LUCK_BONUS.codec()).decode(JsonOps.INSTANCE, effects.getAsJsonArray("minecraft:fishing_luck_bonus")).getOrThrow();
            }
        }

        if (listJsonElementPair != null) {
            jsonObject.get("effects").getAsJsonObject().remove("minecraft:fishing_luck_bonus");
            List<EnchantmentAttributeEffect> effects = new ArrayList<>();
            for (ConditionalEffect<EnchantmentValueEffect> enchantmentValueEffectConditionalEffect : listJsonElementPair.getFirst()) {
                if (enchantmentValueEffectConditionalEffect.effect() instanceof AddValue addValue && addValue.value() instanceof LevelBasedValue.Linear linear) {
                    effects.add(new EnchantmentAttributeEffect(
                            ResourceLocation.withDefaultNamespace("enchantment.fishing_speed"),
                            ModAttributes.FISHING_LUCK,
                            LevelBasedValue.perLevel(linear.base(), linear.perLevelAboveFirst()),
                            AttributeModifier.Operation.ADD_VALUE
                    ));
                }
            }


            JsonElement jsonElement = Objects.requireNonNull(EnchantmentEffectComponents.ATTRIBUTES.codec()).encodeStart(JsonOps.INSTANCE, effects).getOrThrow();
            jsonObject.get("effects").getAsJsonObject().add("minecraft:attributes", jsonElement);
        }
    }
}
