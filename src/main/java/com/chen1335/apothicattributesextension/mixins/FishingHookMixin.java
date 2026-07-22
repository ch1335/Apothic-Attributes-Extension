package com.chen1335.apothicattributesextension.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin extends Entity {
    @Shadow
    private int timeUntilLured;

    @Shadow
    private float fishAngle;

    @Shadow
    private int timeUntilHooked;

    @Shadow
    private int nibble;

    protected FishingHookMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "catchingFish", at = @At("RETURN"))
    private void preventExcessiveLureLock(BlockPos blockPos, CallbackInfo ci) {
        if (this.timeUntilLured <= 0 && this.timeUntilHooked <= 0 && this.nibble <= 0) {
            this.fishAngle = Mth.nextFloat(this.random, 0.0F, 360.0F);
            this.timeUntilHooked = Mth.nextInt(this.random, 20, 80);
        }
    }
}
