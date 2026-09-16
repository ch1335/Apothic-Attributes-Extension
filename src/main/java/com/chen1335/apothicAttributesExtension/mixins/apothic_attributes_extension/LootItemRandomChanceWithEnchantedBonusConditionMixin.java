package com.chen1335.apothicAttributesExtension.mixins.apothic_attributes_extension;

import com.chen1335.apothicAttributesExtension.API.objects.ModAttributes;
import com.chen1335.apothicAttributesExtension.utils.Util;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value = LootItemRandomChanceWithEnchantedBonusCondition.class, priority = 1)
public class LootItemRandomChanceWithEnchantedBonusConditionMixin {
    @Shadow
    @Final
    private Holder<Enchantment> enchantment;

    @WrapOperation(method = "test(Lnet/minecraft/world/level/storage/loot/LootContext;)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getEnchantmentLevel(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/LivingEntity;)I"))
    private int test(Holder<Enchantment> enchantment, LivingEntity entity, Operation<Integer> original, @Local(argsOnly = true) LootContext context) {
        if (enchantment.is(Enchantments.LOOTING)) {
            Entity attacker = context.getParamOrNull(LootContextParams.ATTACKING_ENTITY);
            if (attacker instanceof LivingEntity living) {
                return original.call(enchantment,entity) + Util.toInt(living.getAttributeValue(ModAttributes.MOB_LOOTING), living.getRandom());
            }
        }
        return original.call(enchantment,entity);
    }
}
