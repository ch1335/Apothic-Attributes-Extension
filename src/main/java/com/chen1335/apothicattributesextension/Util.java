package com.chen1335.apothicattributesextension;

import net.minecraft.util.RandomSource;

public final class Util {
    private Util() {
    }

    public static int toInt(double value, RandomSource random) {
        int integer = (int)Math.floor(value);
        return integer + (random.nextDouble() < value - integer ? 1 : 0);
    }
}
