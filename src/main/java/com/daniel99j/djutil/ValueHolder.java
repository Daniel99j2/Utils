package com.daniel99j.djutil;

import java.util.Objects;

public class ValueHolder<T> {
    public T object = null;

    public ValueHolder() {
    }

    public ValueHolder(T o) {
        this.object = o;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ValueHolder<?> that)) return false;
        return Objects.equals(object, that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(object);
    }

    @Override
    public String toString() {
        return "ValueHolder(" + object + ")";
    }
}
