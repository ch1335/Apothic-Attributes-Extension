package com.chen1335.apothicAttributesExtension.mixins;

import com.chen1335.apothicAttributesExtension.API.AttributeTags;
import com.chen1335.apothicAttributesExtension.mixinHooks.AttributesGuiHooks;
import dev.shadowsoffire.apothic_attributes.client.AttributesGui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.neoforge.common.CommonHooks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

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
        Objects.requireNonNull(CommonHooks.resolveLookup(Registries.ATTRIBUTE)).get(AttributeTags.ATTRIBUTE_SORT).ifPresent(holders -> {
            List<Holder<Attribute>> list = holders.stream().toList();
            for (Holder<Attribute> attributeHolder : list) {
                if (attributeHolder.value() == a1.getAttribute().value()) {
                    cir.setReturnValue(-1);
                    break;
                } else if (attributeHolder.value() == a2.getAttribute().value()) {
                    cir.setReturnValue(1);
                    break;
                }
            }
        });
    }

    @Inject(method = "toggleVisibility", at = @At("RETURN"))
    private void toggleVisibility(CallbackInfo ci) {
        AttributesGuiHooks.toggleVisibility((AttributesGui) (Object) this, parent, open, leftPos, topPos);
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void render(GuiGraphics gfx, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        AttributesGuiHooks.render((AttributesGui) (Object) this, gfx, mouseX, mouseY, partialTicks);
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
