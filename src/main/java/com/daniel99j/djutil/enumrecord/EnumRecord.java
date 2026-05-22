package com.daniel99j.djutil.enumrecord;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public abstract class EnumRecord {
    protected static Map<Class<?>, EnumRecordData> data = new HashMap<>();

    protected static <T extends EnumRecord> void init(Class<T> clazz) {
        List<EnumRecordType> records = new ArrayList<>();
        int i = 0;
        for (Field declaredField : clazz.getDeclaredFields()) {
            if(!Modifier.isPublic(declaredField.getModifiers()) || !Modifier.isStatic(declaredField.getModifiers()) || !Modifier.isFinal(declaredField.getModifiers())) {
                throw new IllegalArgumentException("Fields must be static, final, and public");
            }
            try {
                Object o = declaredField.get(null);
                if(o instanceof EnumRecordType enumRecordType) {
                    if(enumRecordType.name != null) throw new IllegalArgumentException("Already initialised");
                    enumRecordType.enumClazz = clazz;
                    enumRecordType.name = declaredField.getName();
                    enumRecordType.ordinal = i;
                    records.add(enumRecordType);
                    i++;
                } else throw new IllegalArgumentException();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        data.put(clazz, new EnumRecordData(records.toArray(new EnumRecordType[0])));
    }

    public static EnumRecordType[] values(Class<?> clazz) {
        if(data.containsKey(clazz)) return data.get(clazz).values();
        throw new IllegalArgumentException("Not an EnumRecord");
    }

    public static EnumRecordType typeOf(Class<?> clazz, String s) {
        if(data.containsKey(clazz)) return Arrays.stream(data.get(clazz).values()).filter((v) -> v.name.equals(s)).findFirst().orElseThrow();
        throw new IllegalArgumentException("Not an EnumRecord");
    }
}
