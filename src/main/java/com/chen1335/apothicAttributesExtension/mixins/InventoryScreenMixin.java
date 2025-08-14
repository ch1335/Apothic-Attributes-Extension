package com.chen1335.apothicAttributesExtension.mixins;

import com.chen1335.apothicAttributesExtension.mixinHooks.AttributesGuiHooks;
import dev.shadowsoffire.apothic_attributes.client.AttributesGui;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {
    @Inject(method = "keyPressed", at = @At("RETURN"))
    public void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        AttributesGuiHooks.keyPressed(keyCode, scanCode, modifiers);
    }
}
