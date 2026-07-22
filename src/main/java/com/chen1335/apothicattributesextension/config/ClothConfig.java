package com.chen1335.apothicattributesextension.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;

public class ClothConfig {
    public static void build(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (mc, parent) -> {
            ConfigBuilder configBuilder = ConfigBuilder.create();
            ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
            configBuilder.setTitle(Component.translatable("apothic_attributes_extension.config"));
            configBuilder.setParentScreen(parent);
            ConfigCategory serverConfig = configBuilder.getOrCreateCategory(Component.translatable("apothic_attributes_extension.config.server"));

            if (Minecraft.getInstance().level != null) {
                @NotNull DoubleListEntry fishingSpeedPerLure = entryBuilder.startDoubleField(Component.translatable("apothic_attributes_extension.fishing_speed_per_lure"), ServerConfig.FISHING_SPEED_PER_LURE.getAsDouble())
                        .setDefaultValue(50)
                        .setSaveConsumer(ServerConfig.FISHING_SPEED_PER_LURE::set)
                        .build();
                fishingSpeedPerLure.setEditable(Minecraft.getInstance().isSingleplayer());
                serverConfig.addEntry(fishingSpeedPerLure);
            }

            configBuilder.setSavingRunnable(() -> {
                if (parent.getMinecraft().level != null || parent.getMinecraft().player != null) {
                    ServerConfig.SPEC.save();
                }
            });
            return configBuilder.build();
        });
    }
}
