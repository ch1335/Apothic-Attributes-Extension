package com.chen1335.apothicattributesextension.mixins;

import com.chen1335.apothicattributesextension.ModAttributes;
import com.chen1335.apothicattributesextension.Util;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = EnchantmentHelper.class, priority = 100)
public class EnchantmentHelperMixin {
    @ModifyReturnValue(method = "getFishingTimeReduction", at = @At("RETURN"))
    private static float addFishingSpeed(float original, @Local(argsOnly = true, name = "fisher") Entity fisher) {
        if (fisher instanceof LivingEntity living) {
            return Math.max(0.0F, original + (float) (living.getAttributeValue(ModAttributes.FISHING_SPEED) / 10.0));
        }
        return original;
    }

    @ModifyReturnValue(method = "getFishingLuckBonus", at = @At("RETURN"))
    private static int addFishingLuck(int original, @Local(argsOnly = true, name = "fisher") Entity fisher) {
        if (fisher instanceof LivingEntity living) {
            return Math.max(0, original + Util.toInt(living.getAttributeValue(ModAttributes.FISHING_LUCK), living.getRandom()));
        }
        return original;
    }
}
