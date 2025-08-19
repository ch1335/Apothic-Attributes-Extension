package com.chen1335.apothicAttributesExtension.mixins.apothic_attributes_extension;

import com.chen1335.apothicAttributesExtension.API.objects.ModAttributes;
import com.chen1335.apothicAttributesExtension.utils.Util;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BonusLevelTableCondition.class)
public class BonusLevelTableConditionMixin {
    @Shadow
    @Final
    private Holder<Enchantment> enchantment;

    @ModifyVariable(method = "test(Lnet/minecraft/world/level/storage/loot/LootContext;)Z", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/item/enchantment/EnchantmentHelper;getItemEnchantmentLevel(Lnet/minecraft/core/Holder;Lnet/minecraft/world/item/ItemStack;)I", ordinal = 0), index = 3)
    private int test(int enchantmentLevel, LootContext context) {
        if (enchantment.is(Enchantments.FORTUNE)) {
            Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);
            if (entity instanceof LivingEntity living) {
                enchantmentLevel = enchantmentLevel + Util.toInt(living.getAttributeValue(ModAttributes.MINING_FORTUNE), living.getRandom());
            }
        }
        return enchantmentLevel;
    }
}
