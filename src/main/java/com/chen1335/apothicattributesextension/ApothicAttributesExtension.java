package com.chen1335.apothicattributesextension;

import com.chen1335.apothicattributesextension.config.ClothConfig;
import com.chen1335.apothicattributesextension.config.ServerConfig;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(com.chen1335.apothicattributesextension.ApothicAttributesExtension.MODID)
public class ApothicAttributesExtension {
    public static final String MODID = "apothic_attributes_extension";
    public ApothicAttributesExtension(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, ServerConfig.SPEC);
        ModAttributes.ATTRIBUTES.register(modEventBus);
        modEventBus.addListener(ApothicAttributesExtension::addEntityAttributes);

        if (ModList.get().isLoaded("cloth_config")) {
            if (FMLEnvironment.getDist().isClient()) {
                ClothConfig.build(modContainer);
            }
        }
    }

    private static void addEntityAttributes(net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent event) {
        event.getTypes().forEach(entityType -> {
            event.add(entityType, ModAttributes.MINING_FORTUNE);
            event.add(entityType, ModAttributes.MOB_LOOTING);
            event.add(entityType, ModAttributes.FISHING_SPEED);
            event.add(entityType, ModAttributes.FISHING_LUCK);
        });
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }
}
