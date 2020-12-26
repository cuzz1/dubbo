package com.cuzz.rpc.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Json工具类，基于Jackson实现
 *
 * @author cuzz
 */
public class JsonUtils {

    /**
     * OBJECT_MAPPER
     */
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        // 忽略不存在的字段
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * 禁止调用无参构造
     *
     * @throws IllegalAccessException e
     */
    private JsonUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Can't create an instance!");
    }


    /**
     * 获取实例
     *
     * @return objectMapper
     */
    public static ObjectMapper instance() {
        return OBJECT_MAPPER;
    }


    /**
     * 将对象转化成 json
     */
    public static <T> String toJson(T t) {
        try {

            return OBJECT_MAPPER.writeValueAsString(t);
        } catch (Exception e) {
            throw new RuntimeException("JSON解析出错");
        }
    }

    /**
     * 将 json 转化成 bean
     */
    public static <T> T toEntity(String json, Class<T> valueType) {
        try {
            return OBJECT_MAPPER.readValue(json, valueType);
        } catch (Exception e) {
            throw new RuntimeException("JSON解析出错");
        }
    }

    /**
     * 将 json 转化成 List
     */
    public static <T> List<T> toList(String json, Class<T> elementClass) {
        return toList(json, ArrayList.class, elementClass);

    }


    /**
     * 将 json 转化成 List
     */
    public static <T> List<T> toList(String json, Class<? extends List> collectionClass, Class<T> elementClass) {
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(collectionClass, elementClass);
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException("JSON解析出错");
        }
    }

    public static <K, V> Map<K, V> toMap(String json, Class<K> keyClass, Class<V> valueClass) {
        return toMap(json, HashMap.class, keyClass, valueClass);
    }

    /**
     * 将 json 转化成 Map
     */
    public static <K, V> Map<K, V> toMap(String json, Class<? extends Map> mapClass, Class<K> keyClass, Class<V> valueClass) {
        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException("JSON解析出错");
        }
    }


}