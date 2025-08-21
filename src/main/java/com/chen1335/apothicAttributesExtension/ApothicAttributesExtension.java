package com.chen1335.apothicAttributesExtension;

import com.chen1335.apothicAttributesExtension.API.objects.ModAttributes;
import com.chen1335.apothicAttributesExtension.config.ClothConfig;
import com.chen1335.apothicAttributesExtension.config.ServerConfig;
import com.chen1335.apothicAttributesExtension.network.ServerConfigPack;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforgespi.Environment;
import org.slf4j.Logger;

@Mod(ApothicAttributesExtension.MODID)
public class ApothicAttributesExtension {
    public static final String MODID = "apothic_attributes_extension";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ApothicAttributesExtension(IEventBus modEventBus, ModContainer modContainer) {
        ServerConfig.load();
        ModAttributes.ATTRIBUTE_DEFERRED_REGISTER.register(modEventBus);
        modEventBus.addListener(EventPriority.LOWEST, ApothicAttributesExtension::EntityAttributeModificationEvent);
        modEventBus.addListener(EventPriority.LOWEST, ApothicAttributesExtension::RegisterPayloadHandlersEvent);
        if (ModList.get().isLoaded("cloth_config")) {
            if (Environment.get().getDist().isClient()) {
                ClothConfig.build(modContainer);
            }
        }
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

    public static void RegisterPayloadHandlersEvent(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(ServerConfigPack.TYPE, ServerConfigPack.STREAM_CODEC, ServerConfigPack::handler);
    }
}
