package com.daniel99j.djutil.maths;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class MathsInterpreter {
    private static String in;
    private static int current;
    private static MathsContext context;

    private static final Map<Integer, Double> cache = new HashMap<>();

    //synchronized makes it not break because current is being changed in many places
    public synchronized static double eval(String s, MathsContext c) {
        int cacheKey = 0;
        if(c.cache) {
            //assumes variables and functions have not changed
            if(c.fastCache) cacheKey = Objects.hash(s, c.variables.keySet(), c.functions.keySet());
            else {
                Map<String, Double> functionOuts = new HashMap<>();
                c.functions.forEach((k, v) -> {
                    functionOuts.put(k, v.apply(12345d));
                });
                cacheKey = Objects.hash(s, c.variables, functionOuts);
            }
            if(cache.containsKey(cacheKey)) return cache.get(cacheKey);
        }
        
        try {
            context = c;
            current = 0;

            AtomicInteger loops = new AtomicInteger();
            StringBuilder newIn = new StringBuilder(s.replace(" ", ""));
            while (true) {
                AtomicBoolean anyChanged = new AtomicBoolean(false);
                context.variables.forEach((k, v) -> {
                    while (newIn.toString().contains(k)) {
                        anyChanged.set(true);
                        int i = newIn.indexOf(k);
                        newIn.replace(i, i+k.length(), "("+v+")");

                        loops.getAndIncrement();
                        if(loops.get() > 1000) throw new MathsParsingError("Variables nested too deeply");
                    }
                });
                in = newIn.toString();
                if(!anyChanged.get()) break;
                loops.getAndIncrement();
                if(loops.get() > 1000) throw new MathsParsingError("Variables nested too deeply");
            }
//
//            while (newIn.toString().split("(?<=\\d)\\(").length > 0) {
//                newIn.toString(.newIn.indexOf("("), newIn.indexOf("(")+1, "(");
//            }

            //5(6) -> 5*(6)
            in = newIn.toString().replaceAll("(?<=[\\d,)])\\(", "*(");

            double out = simpleNumbers();
            if(Double.isInfinite(out) || Double.isNaN(out)) throw new MathsParsingError("Result is not a number");

            if(c.cache) {
                if(cache.size() > 100) cache.clear();
                cache.put(cacheKey, out);
            }
            return out;
        } catch (Exception e) {
            if(e instanceof MathsParsingError) {
                throw e;
            } else {
                throw new MathsParsingError(e.getMessage());
            }
        }
    }

    public synchronized static double eval(String s) {
        return eval(s, MathsContext.create());
    }

    private static double simpleNumbers() {
        double value = multiplyDivide();

        while (current < in.length()) {
            char c = in.charAt(current);

            if (c != '+' && c != '-') {
                break;
            }

            current++;

            double right = multiplyDivide();

            if (c == '+') {
                value += right;
            } else {
                value -= right;
            }
        }

        return value;
    }

    private static double multiplyDivide() {
        double value = power();

        while (current < in.length()) {
            char c = in.charAt(current);

            if (c != '*' && c != '/') {
                break;
            }
            current++;

            double right = power();

            if (c == '*') {
                value *= right;
            } else {
                value /= right;
            }
        }
        return value;
    }

    private static double power() {
        double value = brackets();
        if (current < in.length() && in.charAt(current) == '^') {
            current++;
            double exponent = power();
            value = Math.pow(value, exponent);
        }
        return value;
    }
    private static double brackets() {
        if (in.charAt(current) == '-') {
            current++;
            return -brackets();
        }
        if (Character.isLetter(in.charAt(current))) {
            int start = current;
            while (current < in.length() && (Character.isLetterOrDigit(in.charAt(current)) || in.charAt(current) == '_')) {
                current++;
            }
            String name = in.substring(start, current);
            if (current >= in.length() || in.charAt(current) != '(') {
                throw new MathsParsingError("Missing ( whilst parsing function: " + name);
            }
            current++; //skip the start bracket
            double arg = simpleNumbers();
            if (current >= in.length() || in.charAt(current) != ')') {
                throw new MathsParsingError("Missing ) whilst parsing function: " + name);
            }
            current++; //skip the end bracket

            Function<Double, Double> function = context.functions.get(name);
            if (function == null) {
                throw new MathsParsingError("Unknown function: " + name);
            }
            return function.apply(arg);
        }

        if (in.charAt(current) == '(') {
            current++;
            double value = simpleNumbers();
            if (in.charAt(current) != ')') {
                throw new MathsParsingError("Missing )");
            }
            current++;
            return value;
        }
        //collect the numbers
        int start = current;
        while (current < in.length() && (Character.isDigit(in.charAt(current)) || in.charAt(current) == '.')) {
            current++;
        }
        return Double.parseDouble(in.substring(start, current));
    }
}
