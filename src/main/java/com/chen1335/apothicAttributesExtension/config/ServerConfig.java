package com.chen1335.apothicAttributesExtension.config;

import com.electronwill.nightconfig.core.concurrent.ConcurrentCommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.fml.loading.FMLPaths;

public class ServerConfig {
    public static double FISHING_SPEED_PER_LURE = 50;

    public static void load(CommentedFileConfig config) {
        ConcurrentCommentedConfig serverConfig = ConfigUtils.get(config, "ServerConfig", config.createSubConfig());
        FISHING_SPEED_PER_LURE = ConfigUtils.get(serverConfig, "fishing_speed_per_lure", FISHING_SPEED_PER_LURE);
    }

    public static void load() {
        try (CommentedFileConfig config = CommentedFileConfig.of(FMLPaths.CONFIGDIR.get().resolve("apothic_attributes_extension.toml"))) {
            config.load();
            ServerConfig.load(config);
            config.save();
        }
    }

    public static void save() {
        try (CommentedFileConfig config = CommentedFileConfig.of(FMLPaths.CONFIGDIR.get().resolve("apothic_attributes_extension.toml"))) {
            ServerConfig.load(config);
            config.save();
        }
    }

    public static CompoundTag toCompoundTag() {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putDouble("fishing_speed_per_lure", FISHING_SPEED_PER_LURE);
        return compoundTag;
    }

    public static void loadFromCompoundTag(CompoundTag compoundTag) {
        FISHING_SPEED_PER_LURE = compoundTag.getDouble("fishing_speed_per_lure");
    }
}
