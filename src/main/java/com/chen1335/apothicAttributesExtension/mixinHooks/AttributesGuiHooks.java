package com.chen1335.apothicAttributesExtension.mixinHooks;

import dev.shadowsoffire.apothic_attributes.client.AttributesGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.Component;

public class AttributesGuiHooks {
    public static EditBox nameBox;
    private static AttributesGui ATTRIBUTES_GUI;

    public static void toggleVisibility(AttributesGui attributesGui, InventoryScreen parent, boolean open, int leftPos, int topPos) {
        if (nameBox != null) {
            nameBox.setVisible(open);
            nameBox.setPosition(leftPos + 7, topPos + 3);
        }
    }

    public static void init(AttributesGui attributesGui, InventoryScreen parent, int leftPos, int topPos) {
        ATTRIBUTES_GUI = attributesGui;
        if (nameBox == null) {
            nameBox = new EditBox(Minecraft.getInstance().font, leftPos + 7, topPos + 3, 102, 12, Component.translatable("apothic_attributes_extension.editBox.search"));
            nameBox.visible = false;
        }
    }

    public static void render(AttributesGui attributesGui, GuiGraphics gfx, int mouseX, int mouseY, float partialTicks) {
        if (nameBox != null) {
            nameBox.render(gfx, mouseX, mouseY, partialTicks);
        }
    }

    public static void mouseClicked(AttributesGui attributesGui, double mouseX, double mouseY, int pButton) {
        if (nameBox != null) {
            if (!nameBox.isHovered() && nameBox.isFocused()) {
                nameBox.setFocused(false);
            }
            if (nameBox.isHovered()) {
                nameBox.setFocused(!nameBox.isFocused());
            }
        }
    }

    public static void keyPressed(int keyCode, int scanCode, int modifiers) {
        if (nameBox != null && ATTRIBUTES_GUI != null) {
            nameBox.keyPressed(keyCode, scanCode, modifiers);
            if (nameBox.isActive() && nameBox.isFocused()) {
                ATTRIBUTES_GUI.refreshData();
            }
        }
    }
}
