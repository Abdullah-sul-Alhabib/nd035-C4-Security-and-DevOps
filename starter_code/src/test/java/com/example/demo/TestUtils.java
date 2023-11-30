package com.example.demo;

import java.lang.reflect.Field;

public class TestUtils {
    public static void injectObject(Object target, String fieldName, Object toInjeect){
        boolean wasPrivate = false;
        try {
            Field field = target.getClass().getDeclaredField(fieldName);

            if (!field.isAccessible()) {
                field.setAccessible(true);
                wasPrivate = true;
            }
            field.set(target,toInjeect);
            if (wasPrivate){
                field.setAccessible(false);
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }

}
