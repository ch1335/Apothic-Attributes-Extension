package com.chen1335.apothicAttributesExtension.common;

import com.chen1335.apothicAttributesExtension.ApothicAttributesExtension;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;

@EventBusSubscriber(modid = ApothicAttributesExtension.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void ItemAttributeModifierEvent(ItemAttributeModifierEvent event) {

    }
}
