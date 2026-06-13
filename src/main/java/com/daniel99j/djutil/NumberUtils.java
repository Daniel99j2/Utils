package com.daniel99j.djutil;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings({"unused"})
public class NumberUtils {
    public static int getRandomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static float getRandomFloat(float min, float max) {
        return ThreadLocalRandom.current().nextFloat(min, max);
    }

    public static float makeNotZero(float input) {
        return input == 0 ? 0.00000000000001F : input;
    }

    public static double makeNotZero(double input) {
        return input == 0 ? 0.00000000000001 : input;
    }

    public static Number getMax(Number number) {
        if(number instanceof Integer) return Integer.MAX_VALUE;
        else if(number instanceof Float) return Float.MAX_VALUE;
        else if(number instanceof Short) return Short.MAX_VALUE;
        else if(number instanceof Long) return Long.MAX_VALUE;
        else if(number instanceof Double) return Double.MAX_VALUE;
        throw new IllegalStateException("Not a number");
    }

    public static Number getMin(Number number) {
        if(number instanceof Integer) return Integer.MIN_VALUE;
        else if(number instanceof Float) return Float.MIN_VALUE;
        else if(number instanceof Short) return Short.MIN_VALUE;
        else if(number instanceof Long) return Long.MIN_VALUE;
        else if(number instanceof Double) return Double.MIN_VALUE;
        throw new IllegalStateException("Not a number");
    }

    public static Number convertInto(Number number, Number type) {
        if(type instanceof Integer) {
            return number.intValue();
        }
        else if(type instanceof Float) {
            return number.floatValue();
        }
        else if(type instanceof Short) {
            return number.shortValue();
        }
        else if(type instanceof Long) {
            return number.longValue();
        }
        else if(type instanceof Double) {
            return number.doubleValue();
        }
        throw new IllegalStateException("Not a number");
    }
}
