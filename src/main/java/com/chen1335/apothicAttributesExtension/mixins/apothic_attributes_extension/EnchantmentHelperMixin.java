package com.chen1335.apothicAttributesExtension.mixins.apothic_attributes_extension;

import com.chen1335.apothicAttributesExtension.API.objects.ModAttributes;
import com.chen1335.apothicAttributesExtension.utils.Util;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @ModifyReturnValue(method = "getFishingTimeReduction", at = @At("RETURN"))
    private static float getFishingTimeReduction(float original, @Local(argsOnly = true) Entity entity) {
        if (entity instanceof LivingEntity living) {
            original = (float) (original + living.getAttributeValue(ModAttributes.FISHING_SPEED) / 10);
        }
        return original;
    }

    @ModifyReturnValue(method = "getFishingLuckBonus", at = @At("RETURN"))
    private static int getFishingLuckBonus(int original, @Local(argsOnly = true) Entity entity) {
        if (entity instanceof LivingEntity living) {
            original = original + Util.toInt(living.getAttributeValue(ModAttributes.FISHING_LUCK), entity.getRandom());
        }
        return original;
    }
}
