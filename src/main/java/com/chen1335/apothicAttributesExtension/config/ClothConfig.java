package com.chen1335.apothicAttributesExtension.config;

import com.chen1335.apothicAttributesExtension.network.ServerConfigPack;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class ClothConfig {
    public static void build(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (mc, parent) -> {
            ConfigBuilder configBuilder = ConfigBuilder.create();
            ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
            configBuilder.setTitle(Component.translatable("apothic_attributes_extension.config"));
            configBuilder.setParentScreen(parent);
            ConfigCategory serverConfig = configBuilder.getOrCreateCategory(Component.translatable("apothic_attributes_extension.config.server"));

            @NotNull DoubleListEntry fishingSpeedPerLure = entryBuilder.startDoubleField(Component.translatable("apothic_attributes_extension.fishing_speed_per_lure"), ServerConfig.FISHING_SPEED_PER_LURE)
                    .setDefaultValue(50)
                    .setSaveConsumer(d -> {
                        ServerConfig.FISHING_SPEED_PER_LURE = d;
                    })
                    .build();

            fishingSpeedPerLure.setEditable(canEdit(parent.getMinecraft()));
            serverConfig.addEntry(fishingSpeedPerLure);


            configBuilder.setSavingRunnable(() -> {
                if (parent.getMinecraft().getSingleplayerServer() != null || parent.getMinecraft().player == null) {
                    ServerConfig.save();
                } else if (parent.getMinecraft().player.getPermissionLevel() >= 2) {
                    PacketDistributor.sendToServer(new ServerConfigPack(ServerConfig.toCompoundTag()));
                }
            });
            return configBuilder.build();
        });
    }

    public static boolean canEdit(Minecraft minecraft) {
        if (minecraft.player == null) {
            return true;
        }
        return minecraft.getSingleplayerServer() != null || minecraft.player.getPermissionLevel() >= 2;
    }
}
