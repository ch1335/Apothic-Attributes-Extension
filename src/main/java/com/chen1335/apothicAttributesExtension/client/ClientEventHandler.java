package com.chen1335.apothicAttributesExtension.client;

import com.chen1335.apothicAttributesExtension.ApothicAttributesExtension;
import com.chen1335.apothicAttributesExtension.mixinHooks.AttributesGuiHooks;
import net.minecraft.client.gui.components.EditBox;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

public class ClientEventHandler {
    @EventBusSubscriber(value = {Dist.CLIENT}, modid = ApothicAttributesExtension.MODID)
    public static class Game {
        @SubscribeEvent
        public static void onScreenCharTypedPre(ScreenEvent.CharacterTyped.Pre pre) {
            EditBox nameBox = AttributesGuiHooks.nameBox;
            if (nameBox != null) {
                AttributesGuiHooks.nameBox.charTyped(pre.getCodePoint(), pre.getModifiers());
            }
        }

        @SubscribeEvent
        public static void ScreenClosingEvent(ScreenEvent.Closing pre) {
            EditBox nameBox = AttributesGuiHooks.nameBox;
            if (nameBox != null) {
                nameBox.setFocused(false);
            }
        }
    }
}
