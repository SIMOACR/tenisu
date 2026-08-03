package com.latelier.tenisu.shared.util;

public final class StringUtil {

    private StringUtil() {}

    public static boolean isNullOrBlank(String string) {
        return string == null || string.isBlank();
    }
}
