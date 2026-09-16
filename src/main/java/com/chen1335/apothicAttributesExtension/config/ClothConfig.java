package com.chen1335.apothicAttributesExtension.config;

import com.chen1335.apothicAttributesExtension.network.ServerConfigPack;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.DoubleListEntry;
import me.shedaniel.clothconfig2.gui.entries.StringListListEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ClothConfig {
    public static void build(ModContainer modContainer) {
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (mc, parent) -> {
            ConfigBuilder configBuilder = ConfigBuilder.create();
            ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();
            configBuilder.setTitle(Component.translatable("apothic_attributes_extension.config"));
            configBuilder.setParentScreen(parent);

            Minecraft minecraft = parent.getMinecraft();

            // 客户端配置只影响本地界面，随时可编辑
            ConfigCategory clientConfig = configBuilder.getOrCreateCategory(Component.translatable("apothic_attributes_extension.config.client"));

            @NotNull StringListListEntry attributeSort = entryBuilder.startStrList(Component.translatable("apothic_attributes_extension.attribute_sort"), new ArrayList<>(ClientConfig.getAttributeSort()))
                    .setDefaultValue(new ArrayList<>(ClientConfig.DEFAULT_ATTRIBUTE_SORT))
                    .setSaveConsumer(list -> ClientConfig.setAttributeSort(new ArrayList<>(list)))
                    .setTooltip(Component.translatable("apothic_attributes_extension.attribute_sort.tooltip"))
                    .build();

            clientConfig.addEntry(attributeSort);

            // 服务端配置需要以服务端为准，联机时只有拥有权限的玩家才能提交修改
            ConfigCategory serverConfig = configBuilder.getOrCreateCategory(Component.translatable("apothic_attributes_extension.config.server"));

            @NotNull DoubleListEntry fishingSpeedPerLure = entryBuilder.startDoubleField(Component.translatable("apothic_attributes_extension.fishing_speed_per_lure"), ServerConfig.getFishingSpeedPerLure())
                    .setDefaultValue(50D)
                    .setSaveConsumer(ServerConfig::setFishingSpeedPerLure)
                    .build();

            fishingSpeedPerLure.setEditable(canEditServerConfig(minecraft));
            serverConfig.addEntry(fishingSpeedPerLure);

            configBuilder.setSavingRunnable(() -> {
                ClientConfig.save();
                if (minecraft.getSingleplayerServer() != null || minecraft.player == null) {
                    ServerConfig.save();
                } else if (minecraft.player.getPermissionLevel() >= 2) {
                    PacketDistributor.sendToServer(new ServerConfigPack(ServerConfig.toCompoundTag()));
                }
            });
            return configBuilder.build();
        });
    }

    public static boolean canEditServerConfig(Minecraft minecraft) {
        if (minecraft.player == null) {
            return true;
        }
        return minecraft.getSingleplayerServer() != null || minecraft.player.getPermissionLevel() >= 2;
    }
}
