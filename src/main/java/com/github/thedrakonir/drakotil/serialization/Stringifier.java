package com.github.thedrakonir.drakotil.serialization;

import java.lang.reflect.Field;

public class Stringifier {

    private final static int MAX_DEPTH = 1;

    public static String stringify(Object object) {
        return stringify(object, 0);
    }

    private static String stringify(Object object, int depth) {
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

                stringBuilder.append(field.getName()).append("=").append(depth >= MAX_DEPTH ? "..." : stringify(value, depth + 1)).append(",");
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