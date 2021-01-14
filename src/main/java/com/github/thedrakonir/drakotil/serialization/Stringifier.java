package com.github.thedrakonir.drakotil.serialization;

import java.lang.reflect.Field;

public class Stringifier {

    private static final int DEFAULT_MAX_DEPTH = 1;

    private Stringifier() {
        throw new IllegalStateException("Static only, utility class");
    }

    public static String stringify(Object object) {
        return stringify(object, DEFAULT_MAX_DEPTH, 0);
    }

    public static String stringify(Object object, int recursiveDepth) {
        return stringify(object, recursiveDepth, 0);
    }

    private static String stringify(Object object, int maxDepth, int currentDepth) {
        if (!hasDefaultToString(object)) {
            return object.toString();
        }

        StringBuilder stringBuilder = new StringBuilder(object.getClass().getName()).append("[");
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(object);

                if (value == null) {
                    value = "null";
                }

                stringBuilder.append(field.getName()).append("=").append(currentDepth >= maxDepth ? "..." : stringify(value, maxDepth, currentDepth + 1)).append(",");
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }

        stringBuilder.deleteCharAt(stringBuilder.length() - 1).append("]");
        return stringBuilder.toString();
    }

    private static boolean hasDefaultToString(Object object) {
        String toStringResult = object.toString();
        String defaultResult = object.getClass().getName() + '@' + Integer.toHexString(object.hashCode());

        return toStringResult.equals(defaultResult);
    }
}