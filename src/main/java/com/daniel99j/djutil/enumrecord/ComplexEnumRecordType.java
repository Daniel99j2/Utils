package com.daniel99j.djutil.enumrecord;

import com.daniel99j.djutil.Either;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class ComplexEnumRecordType <T> extends EnumRecordType {
    public EnumRecordValue<T> create(T value) {
        return new EnumRecordValue<>(this, value);
    };
}
