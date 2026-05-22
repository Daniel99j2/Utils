package com.daniel99j.djutil.enumrecord;

public class EnumRecordType {
    protected String name;
    protected int ordinal;
    protected Class<?> enumClazz;

    @Override
    public String toString() {
        return name;
    }

    public int ordinal() {
        return ordinal;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof EnumRecordType enumRecordType) {
            if(!enumRecordType.enumClazz.equals(enumClazz)) return false;
            if(enumRecordType.ordinal == ordinal) return true;
        }
        return false;
    }
}
