package com.chen1335.apothicAttributesExtension.mixins.apothic_attributes_extension;

import com.chen1335.apothicAttributesExtension.config.ClientConfig;
import com.chen1335.apothicAttributesExtension.mixinHooks.AttributesGuiHooks;
import dev.shadowsoffire.apothic_attributes.client.AttributesGui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.List;

@Mixin(AttributesGui.class)
public abstract class AttributesGuiMixin {
    @Shadow
    protected boolean open;

    @Shadow
    @Final
    protected InventoryScreen parent;

    @Shadow
    protected int leftPos;

    @Shadow
    protected int topPos;

    @Shadow
    protected List<AttributeInstance> data;

    @Shadow
    protected int startIndex;

    @Shadow
    protected static float scrollOffset;

    @Shadow
    protected abstract int getOffScreenRows();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(InventoryScreen parent, CallbackInfo ci) {
        AttributesGuiHooks.init((AttributesGui) (Object) this, parent, leftPos, topPos);
    }

    @Inject(method = "compareAttrs", at = @At("RETURN"), cancellable = true)
    private void compareAttrs(AttributeInstance a1, AttributeInstance a2, CallbackInfoReturnable<Integer> cir) {
        int i1 = ClientConfig.getAttributeSortIndex(a1.getAttribute().value());
        int i2 = ClientConfig.getAttributeSortIndex(a2.getAttribute().value());
        if (i1 < 0) {
            if (i2 >= 0) {
                cir.setReturnValue(1);
            }
        } else if (i2 < 0) {
            cir.setReturnValue(-1);
        } else {
            cir.setReturnValue(Integer.compare(i1, i2));
        }
    }

    @Inject(method = "toggleVisibility", at = @At("RETURN"))
    private void toggleVisibility(CallbackInfo ci) {
        AttributesGuiHooks.toggleVisibility((AttributesGui) (Object) this, parent, open, leftPos, topPos);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        AttributesGuiHooks.render((AttributesGui) (Object) this, gfx, mouseX, mouseY, partialTicks, open);
    }

    @Inject(method = "mouseClicked", at = @At("RETURN"))
    private void mouseClicked(double mouseX, double mouseY, int pButton, CallbackInfoReturnable<Boolean> cir) {
        AttributesGuiHooks.mouseClicked((AttributesGui) (Object) this, mouseX, mouseY, pButton);
    }

    @Inject(method = "refreshData", at = @At("RETURN"))
    private void refreshData(CallbackInfo ci) {
        Iterator<AttributeInstance> iterator = data.iterator();
        if (AttributesGuiHooks.nameBox != null) {
            String string = AttributesGuiHooks.nameBox.getValue().toLowerCase();
            while (iterator.hasNext()) {
                AttributeInstance attributeInstance = iterator.next();
                if (!Component.translatable(attributeInstance.getAttribute().value().getDescriptionId()).getString().toLowerCase().contains(string)) {
                    iterator.remove();
                }
            }
            this.startIndex = (int) (scrollOffset * this.getOffScreenRows() + 0.5D);
        }
    }
}
