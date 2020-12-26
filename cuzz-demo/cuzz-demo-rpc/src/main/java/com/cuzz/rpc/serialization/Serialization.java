package com.cuzz.rpc.serialization;

import java.io.IOException;

/**
 * 定义序列化接口
 */
public interface Serialization {
    <T> byte[] serialize(T obj) throws IOException;

    <T> T deSerialize(byte[] data, Class<T> clz) throws IOException;
}