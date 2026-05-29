package com.daniel99j.djutil.maths;

import com.daniel99j.djutil.MiscUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class MathsContext {
    protected final Map<String, String> variables = new HashMap<>();
    protected final Map<String, Function<Double, Double>> functions = new HashMap<>();
    protected boolean cache = true;
    protected boolean fastCache = false;

    private static final Map<String, Function<Double, Double>> DEFAULT_FUNCTIONS = new HashMap<>();

    static {
        for (Method method : Math.class.getMethods()) {
            if(Modifier.isPublic(method.getModifiers()) && Modifier.isStatic(method.getModifiers()) && MiscUtils.classEquals(method.getReturnType(), Double.class) && method.getParameterCount() == 1 && MiscUtils.classEquals(method.getParameters()[0].getType(), Double.class)) DEFAULT_FUNCTIONS.put(method.getName(), (d) -> {
                try {
                    return (Double) method.invoke(null, d);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private MathsContext() {

    }

    public static MathsContext create() {
        MathsContext context = createNoDefaults();
        context.functions.putAll(DEFAULT_FUNCTIONS);
        return context;
    }

    public static MathsContext createNoDefaults() {
        return new MathsContext();
    }

    public MathsContext withFunction(String name, Function<Double, Double> function) {
        functions.put(name, function);
        return this;
    }

    public MathsContext withVariable(String name, String value) {
        return withGlobalVariable("${"+name+"}", value.replace(" ", ""));
    }

    public MathsContext withGlobalVariable(String name, String value) {
        variables.put(name, value.replace(" ", ""));
        return this;
    }

    public MathsContext withFastCache() {
        cache = true;
        fastCache = true;
        return this;
    }

    public MathsContext withCache() {
        cache = true;
        fastCache = false;
        return this;
    }

    public MathsContext withoutCache() {
        cache = false;
        fastCache = false;
        return this;
    }

    public MathsContext copy() {
        MathsContext context = MathsContext.createNoDefaults();
        context.variables.putAll(variables);
        context.functions.putAll(functions);
        context.cache = cache;
        context.fastCache = fastCache;
        return context;
    }
}
