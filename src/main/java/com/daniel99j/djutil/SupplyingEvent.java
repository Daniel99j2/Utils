package com.daniel99j.djutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class SupplyingEvent<T> {
    @FunctionalInterface
    public interface Listener<T> {
        void run(T arg);
    }

    private final List<Listener<T>> handlers = new ArrayList<>();

    public SupplyingEvent() {
    }

    public void register(Listener<T> listener) {
        this.handlers.add(listener);
    }

    public void unregister(Listener<T> listener) {
        this.handlers.remove(listener);
    }

    public void invoke(T arg) {
        for (Listener<T> handler : this.handlers) {
            handler.run(arg);
        }
    }
}
