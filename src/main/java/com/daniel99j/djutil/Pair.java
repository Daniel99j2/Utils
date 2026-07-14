package com.daniel99j.djutil;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record Pair<L, R>(L left, R right) {
    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null) return false;
        if(!(obj instanceof Pair<?, ?>(Object left1, Object right1))) return false;
        return Objects.equals(left, left1) && Objects.equals(right, right1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public @NotNull String toString() {
        return "Pair<" + left + ", " + right + ">";
    }
}
