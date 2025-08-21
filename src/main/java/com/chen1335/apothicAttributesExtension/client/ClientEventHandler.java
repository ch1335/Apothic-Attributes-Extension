package com.chen1335.apothicAttributesExtension.client;

import com.chen1335.apothicAttributesExtension.ApothicAttributesExtension;
import com.chen1335.apothicAttributesExtension.config.ServerConfig;
import com.chen1335.apothicAttributesExtension.mixinHooks.AttributesGuiHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

public class ClientEventHandler {
    @EventBusSubscriber(value = {Dist.CLIENT}, modid = ApothicAttributesExtension.MODID)
    public static class Game {
        @SubscribeEvent
        public static void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
            if (Minecraft.getInstance().getSingleplayerServer() != null) {
                ServerConfig.load();
            }
        }

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
