package com.iostreamonedemo.serialize.protostuffdemo;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

public class ProtoStuffUtil {

    public ProtoStuffUtil() {
    }

    public static <T> byte[] toSerialize(T t) {
        Schema schema = RuntimeSchema.getSchema(t.getClass());
        return ProtostuffIOUtil.toByteArray(t, schema, LinkedBuffer.allocate(256));
    }

    public static <T> T fromSerialize(byte[] bytes, Class<T> clazz) throws IllegalAccessException, InstantiationException {
        T obj = null;
        obj = clazz.newInstance();
        Schema schema = RuntimeSchema.getSchema(obj.getClass());
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);

        return obj;
    }
}
