package com.chen1335.apothicAttributesExtension.utils;

import net.minecraft.util.RandomSource;

public class Util {
    public static int toInt(double value, RandomSource randomSource) {
        int intValue = (int) Math.floor(value);
        return intValue + ((randomSource.nextFloat() < (value - intValue)) ? 1 : 0);
    }
}
