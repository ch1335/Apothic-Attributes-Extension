package com.chen1335.apothicattributesextension;

import com.chen1335.apothicattributesextension.config.ServerConfig;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.enchanting.EnchantedEntityLootEvent;

@EventBusSubscriber(modid = ApothicAttributesExtension.MODID)
public final class EventHandler {
    private EventHandler() {
    }

    @SubscribeEvent
    public static void onItemAttributeModifiers(ItemAttributeModifierEvent event) {
        HolderLookup.RegistryLookup<Enchantment> enchantments = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
        if (enchantments == null) {
            return;
        }

        int lure = event.getItemStack().getEnchantmentLevel(enchantments.getOrThrow(Enchantments.LURE));
        if (lure > 0) {
            event.addModifier(
                ModAttributes.FISHING_SPEED,
                new AttributeModifier(ApothicAttributesExtension.id("enchantment_lure"), lure * ServerConfig.fishingSpeedPerLure(), AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
            );
        }

        addEnchantmentModifier(event, enchantments, Enchantments.LUCK_OF_THE_SEA, ModAttributes.FISHING_LUCK, "enchantment_luck_of_the_sea");
        addEnchantmentModifier(event, enchantments, Enchantments.FORTUNE, ModAttributes.MINING_FORTUNE, "enchantment_fortune");
        addEnchantmentModifier(event, enchantments, Enchantments.LOOTING, ModAttributes.MOB_LOOTING, "enchantment_looting");
    }

    @SubscribeEvent
    public static void onEnchantedEntityLoot(EnchantedEntityLootEvent event) {
        if (!event.getEnchantment().is(Enchantments.LOOTING)) {
            return;
        }

        if (event.getDamageSource().getEntity() instanceof LivingEntity attacker) {
            int bonus = Util.toInt(attacker.getAttributeValue(ModAttributes.MOB_LOOTING), attacker.getRandom());
            event.setEnchantmentLevel(event.getEnchantmentLevel() + bonus);
        }
    }

    private static void addEnchantmentModifier(
        ItemAttributeModifierEvent event,
        HolderLookup.RegistryLookup<Enchantment> enchantments,
        net.minecraft.resources.ResourceKey<Enchantment> enchantment,
        net.minecraft.core.Holder<net.minecraft.world.entity.ai.attributes.Attribute> attribute,
        String modifierId
    ) {
        int level = event.getItemStack().getEnchantmentLevel(enchantments.getOrThrow(enchantment));
        if (level > 0) {
            event.addModifier(
                attribute,
                new AttributeModifier(ApothicAttributesExtension.id(modifierId), level, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
            );
        }
    }
}
