package com.chen1335.apothicattributesextension.mixins;

import com.chen1335.apothicattributesextension.ModAttributes;
import com.chen1335.apothicattributesextension.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
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

@Mixin(BonusLevelTableCondition.class)
public class BonusLevelTableConditionMixin {
    @Shadow
    @Final
    private Holder<Enchantment> enchantment;

    @ModifyExpressionValue(
        method = "test(Lnet/minecraft/world/level/storage/loot/LootContext;)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/neoforged/neoforge/event/EventHooks;getBlockLootEnchantmentLevel(Lnet/minecraft/world/item/ItemInstance;Lnet/minecraft/core/Holder;ILnet/minecraft/world/level/storage/loot/LootContext;)I"
        )
    )
    private int addMiningFortune(int original, @Local(argsOnly = true, name = "context") LootContext context) {
        if (this.enchantment.is(Enchantments.FORTUNE)
            && context.getOptionalParameter(LootContextParams.THIS_ENTITY) instanceof LivingEntity entity) {
            return original + Util.toInt(entity.getAttributeValue(ModAttributes.MINING_FORTUNE), entity.getRandom());
        }
        return original;
    }
}
