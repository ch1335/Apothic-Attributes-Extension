package com.chen1335.apothicattributesextension.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {
    @Shadow
    private int timeUntilLured;

    @Inject(method = "catchingFish", at = @At("RETURN"))
    private void preventExcessiveLureLock(BlockPos blockPos, CallbackInfo ci) {
        timeUntilLured = Math.max(1, timeUntilLured);
    }
}
