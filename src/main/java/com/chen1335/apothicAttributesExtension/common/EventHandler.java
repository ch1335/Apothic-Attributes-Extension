package com.chen1335.apothicAttributesExtension.common;

import com.chen1335.apothicAttributesExtension.API.objects.ModAttributes;
import com.chen1335.apothicAttributesExtension.ApothicAttributesExtension;
import com.chen1335.apothicAttributesExtension.config.ServerConfig;
import com.chen1335.apothicAttributesExtension.network.ServerConfigPack;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = ApothicAttributesExtension.MODID)
public class EventHandler {
    @SubscribeEvent
    public static void PlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (!event.getEntity().isLocalPlayer()) {
            PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(),new ServerConfigPack(ServerConfig.toCompoundTag()));
        }
    }


    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void ItemAttributeModifierEvent(ItemAttributeModifierEvent event) {
        HolderLookup.@Nullable RegistryLookup<Enchantment> registryLookup = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
        if (registryLookup != null) {
            int lureLevel = event.getItemStack().getEnchantmentLevel(registryLookup.getOrThrow(Enchantments.LURE));
            if (lureLevel > 0) {
                event.addModifier(ModAttributes.FISHING_SPEED, new AttributeModifier(ApothicAttributesExtension.id("enchantment_lure"), lureLevel * ServerConfig.FISHING_SPEED_PER_LURE, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            }

            int luckOfTheSeaLevel = event.getItemStack().getEnchantmentLevel(registryLookup.getOrThrow(Enchantments.LUCK_OF_THE_SEA));
            if (luckOfTheSeaLevel > 0) {
                event.addModifier(ModAttributes.FISHING_LUCK, new AttributeModifier(ApothicAttributesExtension.id("enchantment_luck_of_the_sea"), luckOfTheSeaLevel, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            }

            int fortuneLevel = event.getItemStack().getEnchantmentLevel(registryLookup.getOrThrow(Enchantments.FORTUNE));
            if (fortuneLevel > 0) {
                event.addModifier(ModAttributes.MINING_FORTUNE, new AttributeModifier(ApothicAttributesExtension.id("enchantment_fortune"), fortuneLevel, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            }

            int lootingLevel = event.getItemStack().getEnchantmentLevel(registryLookup.getOrThrow(Enchantments.LOOTING));
            if (lootingLevel > 0) {
                event.addModifier(ModAttributes.MOB_LOOTING, new AttributeModifier(ApothicAttributesExtension.id("enchantment_looting"), lootingLevel, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND);
            }
        }
    }
}
