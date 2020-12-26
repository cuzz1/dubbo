package com.cuzz.rpc.serialization;

public class SerializationFactory {
    public static Serialization get(byte extraInfo) {
        return new HessianSerialization();
    }
}
