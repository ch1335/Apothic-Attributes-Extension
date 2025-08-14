package com.chen1335.apothicAttributesExtension.mixins;

import com.chen1335.apothicAttributesExtension.API.objects.ModAttributes;
import com.chen1335.apothicAttributesExtension.utils.Util;
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
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ApplyBonusCount.class)
public class ApplyBonusCountMixin {
    @Shadow
    @Final
    private Holder<Enchantment> enchantment;

    @ModifyVariable(
            method = "run",
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ItemStack;)I",
                    ordinal = 0),
            index = 4
    )
    private int applyEnchantBonus(int enchantmentLevel, ItemStack stack, LootContext context) {
        if (enchantment.is(Enchantments.FORTUNE)) {
            Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
            if (entity instanceof LivingEntity living) {
                enchantmentLevel = enchantmentLevel + Util.toInt(living.getAttributeValue(ModAttributes.MINING_FORTUNE), living.getRandom());
            }
        }
        return enchantmentLevel;
    }
}
