package com.chen1335.apothicAttributesExtension.config;

import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ServerConfig {
    public static final ServerConfig INSTANCE;
    public static final ModConfigSpec SPEC;

    public final ModConfigSpec.DoubleValue fishingSpeedPerLure;

    static {
        Pair<ServerConfig, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(ServerConfig::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }

    private ServerConfig(ModConfigSpec.Builder builder) {
        fishingSpeedPerLure = builder
                .comment("Fishing speed granted per level of the Lure enchantment.")
                .defineInRange("fishing_speed_per_lure", 50.0D, 0.0D, Double.MAX_VALUE);
    }

    /**
     * 读取饵钓加成值。客户端的 SERVER 配置在连接服务端之前尚未加载，此时回退到默认值。
     */
    public static double getFishingSpeedPerLure() {
        return SPEC.isLoaded() ? INSTANCE.fishingSpeedPerLure.get() : INSTANCE.fishingSpeedPerLure.getDefault();
    }

    public static void setFishingSpeedPerLure(double value) {
        if (SPEC.isLoaded()) {
            INSTANCE.fishingSpeedPerLure.set(value);
        }
    }

    /**
     * 将内存中的配置写回配置文件。
     */
    public static void save() {
        if (!SPEC.isLoaded()) {
            return;
        }
        INSTANCE.fishingSpeedPerLure.save();
    }

    public static CompoundTag toCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putDouble("fishing_speed_per_lure", getFishingSpeedPerLure());
        return compoundTag;
    }

    /**
     * 用给定的数据覆盖内存中的配置，不写入配置文件。
     */
    public static void loadFromCompoundTag(CompoundTag compoundTag) {
        if (compoundTag.contains("fishing_speed_per_lure")) {
            setFishingSpeedPerLure(compoundTag.getDouble("fishing_speed_per_lure"));
        }
    }
}
