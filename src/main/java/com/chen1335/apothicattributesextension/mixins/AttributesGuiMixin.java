package com.chen1335.apothicattributesextension.mixins;

import com.chen1335.apothicattributesextension.AttributeTags;
import com.chen1335.apothicattributesextension.mixinsAPI.AttributesGuiExtension;
import dev.shadowsoffire.apothic_attributes.client.AttributesGui;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.neoforge.common.CommonHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AttributesGui.class, remap = false)
public abstract class AttributesGuiMixin implements AttributesGuiExtension {
    @Shadow
    protected boolean open;

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

    @Unique
    private EditBox apothicAttributesExtension$search;

    @Unique
    private Map<Holder<Attribute>, Integer> apothicAttributesExtension$sortRanks = Map.of();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void initializeSearch(InventoryScreen parent, CallbackInfo ci) {
        this.apothicAttributesExtension$search = new EditBox(
            Minecraft.getInstance().font,
            this.leftPos + 7,
            this.topPos + 3,
            102,
            12,
            Component.translatable("apothic_attributes_extension.gui.attribute_search")
        );
        this.apothicAttributesExtension$search.visible = false;
        this.apothicAttributesExtension$search.setResponder(ignored -> ((AttributesGui)(Object)this).refreshData());
        this.apothicAttributesExtension$refreshSortRanks();
    }

    @Inject(method = "toggleVisibility", at = @At("RETURN"))
    private void updateSearchVisibility(CallbackInfo ci) {
        this.apothicAttributesExtension$search.visible = this.open;
        this.apothicAttributesExtension$search.setPosition(this.leftPos + 7, this.topPos + 3);
        if (!this.open) {
            this.apothicAttributesExtension$search.setFocused(false);
        }
    }

    @Inject(method = "extractRenderState", at = @At("RETURN"))
    private void renderSearch(GuiGraphicsExtractor gfx, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (this.open) {
            this.apothicAttributesExtension$search.extractRenderState(gfx, mouseX, mouseY, partialTicks);
        }
    }

    @Inject(method = "mouseClicked", at = @At("HEAD"), cancellable = true)
    private void clickSearch(MouseButtonEvent event, boolean doubleClick, CallbackInfoReturnable<Boolean> cir) {
        if (this.open && this.apothicAttributesExtension$search.mouseClicked(event, doubleClick)) {
            this.apothicAttributesExtension$search.setFocused(true);
            cir.setReturnValue(true);
        } else if (this.apothicAttributesExtension$search.isFocused()) {
            this.apothicAttributesExtension$search.setFocused(false);
        }
    }

    @Inject(method = "refreshData", at = @At("RETURN"))
    private void filterSearchResults(CallbackInfo ci) {
        String query = this.apothicAttributesExtension$search == null ? "" : this.apothicAttributesExtension$search.getValue().strip().toLowerCase(Locale.ROOT);
        if (!query.isEmpty()) {
            this.data.removeIf(instance -> !I18n.get(instance.getAttribute().value().getDescriptionId()).toLowerCase(Locale.ROOT).contains(query));
        }
        this.startIndex = Math.min(this.startIndex, this.getOffScreenRows());
        scrollOffset = this.getOffScreenRows() == 0 ? 0.0F : (float)this.startIndex / (float)this.getOffScreenRows();
    }

    @Inject(method = "compareAttrs", at = @At("HEAD"), cancellable = true)
    private void sortTaggedAttributes(AttributeInstance a1, AttributeInstance a2, CallbackInfoReturnable<Integer> cir) {
        Integer firstRank = this.apothicAttributesExtension$sortRanks.get(a1.getAttribute());
        Integer secondRank = this.apothicAttributesExtension$sortRanks.get(a2.getAttribute());
        if (firstRank != null || secondRank != null) {
            cir.setReturnValue(Integer.compare(firstRank == null ? Integer.MAX_VALUE : firstRank, secondRank == null ? Integer.MAX_VALUE : secondRank));
        }
    }

    @Unique
    public boolean apothicAttributesExtension$keyPressed(KeyEvent event) {
        return this.open && this.apothicAttributesExtension$search.isFocused() && this.apothicAttributesExtension$search.keyPressed(event);
    }

    @Unique
    public boolean apothicAttributesExtension$charTyped(CharacterEvent event) {
        return this.open && this.apothicAttributesExtension$search.isFocused() && this.apothicAttributesExtension$search.charTyped(event);
    }

    @Unique
    private void apothicAttributesExtension$refreshSortRanks() {
        HolderLookup.RegistryLookup<Attribute> lookup = CommonHooks.resolveLookup(Registries.ATTRIBUTE);
        if (lookup == null) {
            return;
        }
        lookup.get(AttributeTags.ATTRIBUTE_SORT).ifPresent(tag -> {
            Map<Holder<Attribute>, Integer> ranks = new HashMap<>();
            int rank = 0;
            for (Holder<Attribute> attribute : tag) {
                ranks.put(attribute, rank++);
            }
            this.apothicAttributesExtension$sortRanks = ranks;
        });
    }
}
