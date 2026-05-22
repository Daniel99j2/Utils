package com.daniel99j.djutil.enumrecord;

public record EnumRecordValue<T>(ComplexEnumRecordType<T> enumType, T value) {
    @Override
    public boolean equals(Object o) {
        if(o instanceof EnumRecordType type) {
            return type.equals(enumType);
        };
        if(o instanceof EnumRecordValue value) {
            return this.equals(value.enumType);
        }
        return false;
    }
}
