package com.chen1335.apothicAttributesExtension.mixins.apothic_attributes_extension;

import com.chen1335.apothicAttributesExtension.API.objects.ModAttributes;
import com.chen1335.apothicAttributesExtension.utils.Util;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = ApplyBonusCount.class, priority = 1)
public class ApplyBonusCountMixin {
    @Shadow
    @Final
    private Holder<Enchantment> enchantment;

    @WrapOperation(
            method = "run",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ItemStack;)I",
                    ordinal = 0)
    )
    private int applyEnchantBonus(Holder<Enchantment> enchantment, ItemStack stack, Operation<Integer> original, @Local(argsOnly = true) LootContext context) {
        if (enchantment.is(Enchantments.FORTUNE)) {
            Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
            if (entity instanceof LivingEntity living) {
                return original.call(enchantment, stack) + Util.toInt(living.getAttributeValue(ModAttributes.MINING_FORTUNE), living.getRandom());
            }
        }
        return original.call(enchantment, stack);
    }
}
