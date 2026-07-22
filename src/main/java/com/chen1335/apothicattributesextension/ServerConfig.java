package com.chen1335.apothicattributesextension;

import net.neoforged.neoforge.common.ModConfigSpec;

public final class ServerConfig {
    public static final double DEFAULT_FISHING_SPEED_PER_LURE = 50.0;
    public static final double MIN_FISHING_SPEED_PER_LURE = 0.0;
    public static final double MAX_FISHING_SPEED_PER_LURE = 100000.0;

    public static final ModConfigSpec SPEC;
    private static final ModConfigSpec.DoubleValue FISHING_SPEED_PER_LURE;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("ServerConfig");
        FISHING_SPEED_PER_LURE = builder
            .comment("The amount of fishing speed granted by each level of Lure.")
            .translation("apothic_attributes_extension.fishing_speed_per_lure")
            .defineInRange(
                "fishing_speed_per_lure",
                DEFAULT_FISHING_SPEED_PER_LURE,
                MIN_FISHING_SPEED_PER_LURE,
                MAX_FISHING_SPEED_PER_LURE
            );
        builder.pop();
        SPEC = builder.build();
    }

    private ServerConfig() {
    }

    public static double fishingSpeedPerLure() {
        return FISHING_SPEED_PER_LURE.getAsDouble();
    }
}
