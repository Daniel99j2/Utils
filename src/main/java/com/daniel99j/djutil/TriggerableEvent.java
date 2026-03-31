package com.daniel99j.djutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class TriggerableEvent<T extends Runnable> {
    private final List<T> handlers = new ArrayList<>();

    public TriggerableEvent() {
    }

    public void register(T listener) {
        this.handlers.add(listener);
    }

    public void unregister(T listener) {
        this.handlers.remove(listener);
    }

    public void invoke() {
        for (T handler : this.handlers) {
            handler.run();
        }
    }
}
