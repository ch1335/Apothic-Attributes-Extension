package com.chen1335.apothicAttributesExtension.config;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * 纯客户端配置，只影响本地属性界面的显示顺序，不参与服务端同步。
 */
public class ClientConfig {
    public static final ClientConfig INSTANCE;
    public static final ModConfigSpec SPEC;

    /**
     * 属性界面的默认排序顺序，列表越靠前显示越靠前。
     * 未列出的属性按本地化名称排序，并排在所有已列出的属性之后。
     */
    public static final List<String> DEFAULT_ATTRIBUTE_SORT = List.of(
            "minecraft:generic.attack_damage",
            "minecraft:generic.attack_speed",
            "minecraft:generic.max_health",
            "minecraft:generic.armor",
            "minecraft:generic.armor_toughness",
            "minecraft:generic.movement_speed",
            "apothic_attributes_extension:mob_looting",
            "apothic_attributes_extension:mining_fortune",
            "apothic_attributes_extension:fishing_speed",
            "apothic_attributes_extension:fishing_luck",
            "irons_spellbooks:max_mana",
            "irons_spellbooks:spell_power"
    );

    public final ModConfigSpec.ConfigValue<List<? extends String>> attributeSort;

    static {
        Pair<ClientConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(ClientConfig::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }

    private ClientConfig(ModConfigSpec.Builder builder) {
        attributeSort = builder
                .comment("Display order of the attributes screen. Attributes listed earlier are shown first.",
                        "Attributes not listed here are sorted by their localized name and shown after all listed ones.",
                        "Each entry is an attribute ID, for example minecraft:generic.attack_damage")
                .defineListAllowEmpty("attribute_sort", new ArrayList<>(DEFAULT_ATTRIBUTE_SORT),
                        () -> "minecraft:generic.max_health", ClientConfig::isValidAttributeId);
    }

    private static boolean isValidAttributeId(Object value) {
        return value instanceof String string && ResourceLocation.tryParse(string) != null;
    }

    /**
     * 读取属性排序列表，配置尚未加载时回退到默认值。
     */
    public static List<? extends String> getAttributeSort() {
        return SPEC.isLoaded() ? INSTANCE.attributeSort.get() : DEFAULT_ATTRIBUTE_SORT;
    }

    public static void setAttributeSort(List<? extends String> value) {
        if (SPEC.isLoaded()) {
            INSTANCE.attributeSort.set(value);
        }
    }

    /**
     * 返回属性在配置的排序列表中的下标，属性不在列表中时返回 -1。
     */
    public static int getAttributeSortIndex(Attribute attribute) {
        ResourceLocation id = BuiltInRegistries.ATTRIBUTE.getKey(attribute);
        if (id == null) {
            return -1;
        }
        String idString = id.toString();
        List<? extends String> order = getAttributeSort();
        for (int i = 0; i < order.size(); i++) {
            if (idString.equals(order.get(i))) {
                return i;
            }
        }
        return -1;
    }

    public static void save() {
        if (SPEC.isLoaded()) {
            INSTANCE.attributeSort.save();
        }
    }
}
