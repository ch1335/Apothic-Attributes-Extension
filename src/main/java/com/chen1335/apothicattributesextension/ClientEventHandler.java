package com.chen1335.apothicattributesextension;

import com.chen1335.apothicattributesextension.mixinsAPI.AttributesGuiExtension;
import dev.shadowsoffire.apothic_attributes.client.AttributesGui;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = ApothicAttributesExtension.MODID, value = Dist.CLIENT)
public final class ClientEventHandler {
    private static AttributesGui activeAttributesGui;

    private ClientEventHandler() {
    }

    @SubscribeEvent
    public static void onScreenInitialized(ScreenEvent.Init.Post event) {
        if (event.getScreen() instanceof InventoryScreen) {
            for (var listener : event.getListenersList()) {
                if (listener instanceof AttributesGui attributesGui) {
                    activeAttributesGui = attributesGui;
                    return;
                }
            }
        }
        activeAttributesGui = null;
    }

    @SubscribeEvent
    public static void onKeyPressed(ScreenEvent.KeyPressed.Pre event) {
        if (event.getScreen() instanceof InventoryScreen
            && activeAttributesGui instanceof AttributesGuiExtension extension
            && extension.apothicAttributesExtension$keyPressed(event.getKeyEvent())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onCharacterTyped(ScreenEvent.CharacterTyped.Pre event) {
        if (event.getScreen() instanceof InventoryScreen
            && activeAttributesGui instanceof AttributesGuiExtension extension
            && extension.apothicAttributesExtension$charTyped(event.getCharacterEvent())) {
            event.setCanceled(true);
        }
    }
}
