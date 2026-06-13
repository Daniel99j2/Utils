package com.daniel99j.djutil;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MiscUtils {
    public static String getTextBetween(String text, String start, String end) {
        int startIndex = text.indexOf(start);
        if (startIndex == -1) return ""; // start string not found

        startIndex += start.length(); // skip past start
        int endIndex = text.indexOf(end, startIndex);
        if (endIndex == -1) return ""; // end string not found

        return text.substring(startIndex, endIndex);
    }


    public static String replaceTextBetween(String text, String start, String end, String replacement) {
        int startIndex = text.indexOf(start);
        if (startIndex == -1) return text; // start string not found

        int endIndex = text.indexOf(end, startIndex);
        if (endIndex == -1) return text; // end string not found

        endIndex += end.length(); // include the end
        return text.substring(0, startIndex) + replacement + text.substring(endIndex);
    }

    public static <T> T fallback(@Nullable T check, T fallback) {
        return check != null ? check : fallback;
    }

    public static boolean containsArg(String name, String[] args) {
        for (String arg : args) {
            if(arg.equals(name)) return true;
        }
        return false;
    }

    @Nullable
    public static String getArgValue(String name, String[] args) {
        int i = 0;
        for (String arg : args) {
            if(arg.equals(name)) return args[i+1];
            i++;
        }
        return null;
    }

    public static Class<?> getClassNonPrimitive(Class<?> objectOrPrimitive) {
        if(objectOrPrimitive.equals(double.class)) return Double.class;
        if(objectOrPrimitive.equals(float.class)) return Float.class;
        if(objectOrPrimitive.equals(int.class)) return Integer.class;
        if(objectOrPrimitive.equals(long.class)) return Long.class;
        if(objectOrPrimitive.equals(short.class)) return Short.class;
        if(objectOrPrimitive.equals(byte.class)) return Byte.class;
        if(objectOrPrimitive.equals(char.class)) return Character.class;
        if(objectOrPrimitive.equals(boolean.class)) return Boolean.class;
        return objectOrPrimitive;
    }

    public static boolean classEquals(Class<?> objectOrPrimitive, Class<?> object) {
        if(objectOrPrimitive.equals(double.class) && object.equals(Double.class)) return true;
        if(objectOrPrimitive.equals(float.class) && object.equals(Float.class)) return true;
        if(objectOrPrimitive.equals(int.class) && object.equals(Integer.class)) return true;
        if(objectOrPrimitive.equals(long.class) && object.equals(Long.class)) return true;
        if(objectOrPrimitive.equals(short.class) && object.equals(Short.class)) return true;
        if(objectOrPrimitive.equals(byte.class) && object.equals(Byte.class)) return true;
        if(objectOrPrimitive.equals(char.class) && object.equals(Character.class)) return true;
        if(objectOrPrimitive.equals(boolean.class) && object.equals(Boolean.class)) return true;
        return objectOrPrimitive.equals(object);
    }
}
