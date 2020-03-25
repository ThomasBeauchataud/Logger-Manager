package com.github.ffcfalcos.logger.util;

import com.github.ffcfalcos.logger.interceptor.LogIgnored;
import com.github.ffcfalcos.logger.interceptor.JsonSerializable;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Thomas Beauchataud
 * @since 24.02.2020
 * Serializing service class
 */
public class SerializerService {

    /**
     * Serialize a object into a map object to be converted in json
     *
     * @param object Object
     * @return Object
     */
    public static Object serializeJson(Object object) {
        return serializeJsonInternal(object);
    }

    /**
     * Recursively serialize object fields
     *
     * @param object Object
     * @return Object
     */
    private static Object serializeJsonInternal(Object object) {
        if (object == null) {
            return "null";
        }
        if (object.getClass().isArray() || object instanceof Collection) {
            return object;
        }
        if (object.getClass().isPrimitive() || object.getClass().getDeclaredAnnotation(JsonSerializable.class) == null) {
            return object.toString();
        }
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        Field[] superFields = object.getClass().getSuperclass().getDeclaredFields();
        Field[] fieldList = new Field[superFields.length + fields.length];
        System.arraycopy(superFields, 0, fieldList, 0, superFields.length);
        System.arraycopy(fields, 0, fieldList, superFields.length, fields.length);
        for (Field field : fieldList) {
            if (!java.lang.reflect.Modifier.isStatic(field.getModifiers()) && field.getDeclaredAnnotation(LogIgnored.class) == null) {
                field.setAccessible(true);
                try {
                    map.put(field.getName(), serializeJsonInternal(field.get(object)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }
}
