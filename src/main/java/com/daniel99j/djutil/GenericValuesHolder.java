package com.daniel99j.djutil;

//for some reason the last value has errors :(
public record GenericValuesHolder<A, B, C, D, USELESS>(A a, B b, C c, D d, USELESS useless) {
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
}
