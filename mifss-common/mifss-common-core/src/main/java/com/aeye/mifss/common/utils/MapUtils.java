package com.aeye.mifss.common.utils;

import java.util.*;

/**
 * Map工具类
 *
 * @author mifss
 */
public class MapUtils {

    /**
     * 判断Map是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * 判断Map是否非空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 创建HashMap
     */
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    /**
     * 创建指定容量的HashMap
     */
    public static <K, V> HashMap<K, V> newHashMap(int size) {
        return new HashMap<>((int) (size / 0.75) + 1);
    }

    /**
     * 创建LinkedHashMap
     */
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    /**
     * 创建TreeMap
     */
    public static <K, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<>();
    }

    /**
     * 快速构建Map（键值对方式）
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> of(Object... keyValues) {
        if (keyValues == null || keyValues.length == 0) {
            return new HashMap<>();
        }
        if (keyValues.length % 2 != 0) {
            throw new IllegalArgumentException("键值对数量必须为偶数");
        }
        Map<K, V> map = new HashMap<>((keyValues.length / 2) + 1);
        for (int i = 0; i < keyValues.length; i += 2) {
            map.put((K) keyValues[i], (V) keyValues[i + 1]);
        }
        return map;
    }

    /**
     * 获取Map值并转换为String
     */
    public static String getStr(Map<?, ?> map, Object key) {
        return getStr(map, key, null);
    }

    /**
     * 获取Map值并转换为String（带默认值）
     */
    public static String getStr(Map<?, ?> map, Object key, String defaultValue) {
        if (isEmpty(map)) {
            return defaultValue;
        }
        Object value = map.get(key);
        return value != null ? value.toString() : defaultValue;
    }

    /**
     * 获取Map值并转换为Integer
     */
    public static Integer getInt(Map<?, ?> map, Object key) {
        return getInt(map, key, null);
    }

    /**
     * 获取Map值并转换为Integer（带默认值）
     */
    public static Integer getInt(Map<?, ?> map, Object key, Integer defaultValue) {
        if (isEmpty(map)) {
            return defaultValue;
        }
        Object value = map.get(key);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取Map值并转换为Long
     */
    public static Long getLong(Map<?, ?> map, Object key) {
        return getLong(map, key, null);
    }

    /**
     * 获取Map值并转换为Long（带默认值）
     */
    public static Long getLong(Map<?, ?> map, Object key, Long defaultValue) {
        if (isEmpty(map)) {
            return defaultValue;
        }
        Object value = map.get(key);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        try {
            return Long.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取Map值并转换为Double
     */
    public static Double getDouble(Map<?, ?> map, Object key) {
        return getDouble(map, key, null);
    }

    /**
     * 获取Map值并转换为Double（带默认值）
     */
    public static Double getDouble(Map<?, ?> map, Object key, Double defaultValue) {
        if (isEmpty(map)) {
            return defaultValue;
        }
        Object value = map.get(key);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        try {
            return Double.valueOf(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * 获取Map值并转换为Boolean
     */
    public static Boolean getBool(Map<?, ?> map, Object key) {
        return getBool(map, key, null);
    }

    /**
     * 获取Map值并转换为Boolean（带默认值）
     */
    public static Boolean getBool(Map<?, ?> map, Object key, Boolean defaultValue) {
        if (isEmpty(map)) {
            return defaultValue;
        }
        Object value = map.get(key);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        return Boolean.valueOf(value.toString());
    }

    /**
     * 合并多个Map
     */
    @SafeVarargs
    public static <K, V> Map<K, V> merge(Map<K, V>... maps) {
        Map<K, V> result = new HashMap<>();
        if (maps != null) {
            for (Map<K, V> map : maps) {
                if (isNotEmpty(map)) {
                    result.putAll(map);
                }
            }
        }
        return result;
    }

    /**
     * 过滤Map中的null值
     */
    public static <K, V> Map<K, V> filterNull(Map<K, V> map) {
        if (isEmpty(map)) {
            return map;
        }
        Map<K, V> result = new HashMap<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        return result;
    }

    /**
     * 过滤Map中的空字符串值
     */
    public static Map<String, Object> filterEmpty(Map<String, Object> map) {
        if (isEmpty(map)) {
            return map;
        }
        Map<String, Object> result = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            Object value = entry.getValue();
            if (value != null) {
                if (value instanceof String) {
                    if (StringUtils.isNotEmpty((String) value)) {
                        result.put(entry.getKey(), value);
                    }
                } else {
                    result.put(entry.getKey(), value);
                }
            }
        }
        return result;
    }

    /**
     * 反转Map的键值
     */
    public static <K, V> Map<V, K> reverse(Map<K, V> map) {
        if (isEmpty(map)) {
            return new HashMap<>();
        }
        Map<V, K> result = new HashMap<>(map.size());
        for (Map.Entry<K, V> entry : map.entrySet()) {
            result.put(entry.getValue(), entry.getKey());
        }
        return result;
    }

    /**
     * 获取所有键的列表
     */
    public static <K> List<K> keys(Map<K, ?> map) {
        if (isEmpty(map)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(map.keySet());
    }

    /**
     * 获取所有值的列表
     */
    public static <V> List<V> values(Map<?, V> map) {
        if (isEmpty(map)) {
            return new ArrayList<>();
        }
        return new ArrayList<>(map.values());
    }
}
