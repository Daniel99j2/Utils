package com.daniel99j.djutil;

import org.jetbrains.annotations.NotNull;

public record GenericValuesHolder<A, B, C, D, E>(A a, B b, C c, D d, E e) {
    public GenericValuesHolder(A a) {
        this(a, null, null, null, null);
    }
    public GenericValuesHolder(A a, B b) {
        this(a, b, null, null, null);
    }
    public GenericValuesHolder(A a, B b, C c) {
        this(a, b, c, null, null);
    }
    public GenericValuesHolder(A a, B b, C c, D d) {
        this(a, b, c, d, null);
    }

    @Override
    public @NotNull String toString() {
        return "GenericValuesHolder(" + a + ", " + b + ", " + c + ", " + d + ", " + e + ")";
    }
}
