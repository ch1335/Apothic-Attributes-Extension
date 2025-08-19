package com.chen1335.apothicAttributesExtension;

import com.chen1335.apothicAttributesExtension.API.objects.ModAttributes;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import org.slf4j.Logger;

@Mod(ApothicAttributesExtension.MODID)
public class ApothicAttributesExtension {
    public static final String MODID = "apothic_attributes_extension";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ApothicAttributesExtension(IEventBus modEventBus, ModContainer modContainer) {
        ModAttributes.ATTRIBUTE_DEFERRED_REGISTER.register(modEventBus);
        modEventBus.addListener(EventPriority.LOWEST, ApothicAttributesExtension::EntityAttributeModificationEvent);
    }

    public static ResourceLocation id(String id) {
        return ResourceLocation.fromNamespaceAndPath(MODID, id);
    }

    public static void EntityAttributeModificationEvent(EntityAttributeModificationEvent event) {
        event.getTypes().forEach(entityType -> {
            event.add(entityType, ModAttributes.MINING_FORTUNE);
            event.add(entityType, ModAttributes.MOB_LOOTING);
            event.add(entityType, ModAttributes.FISHING_SPEED);
            event.add(entityType, ModAttributes.FISHING_LUCK);
        });
    }
}
