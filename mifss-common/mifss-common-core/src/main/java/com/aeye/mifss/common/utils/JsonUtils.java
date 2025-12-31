package com.aeye.mifss.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * JSON工具类
 * 基于Fastjson实现
 *
 * @author mifss
 */
public class JsonUtils {

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    /**
     * 对象转JSON字符串
     */
    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return JSON.toJSONString(object);
        } catch (Exception e) {
            log.error("对象转JSON字符串失败", e);
            throw new RuntimeException("对象转JSON字符串失败", e);
        }
    }

    /**
     * 对象转JSON字符串（格式化输出）
     */
    public static String toPrettyJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            return JSON.toJSONString(object, SerializerFeature.PrettyFormat);
        } catch (Exception e) {
            log.error("对象转JSON字符串失败", e);
            throw new RuntimeException("对象转JSON字符串失败", e);
        }
    }

    /**
     * 对象转JSON字符串（包含null值）
     */
    public static String toJsonWithNull(Object object) {
        if (object == null) {
            return null;
        }
        return JSON.toJSONString(object, SerializerFeature.WriteMapNullValue);
    }

    /**
     * JSON字符串转对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            log.error("JSON字符串转对象失败，json: {}", json, e);
            throw new RuntimeException("JSON字符串转对象失败", e);
        }
    }

    /**
     * JSON字符串转对象（支持泛型）
     */
    public static <T> T parseObject(String json, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON.parseObject(json, typeReference);
        } catch (Exception e) {
            log.error("JSON字符串转对象失败，json: {}", json, e);
            throw new RuntimeException("JSON字符串转对象失败", e);
        }
    }

    /**
     * JSON字符串转List
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON.parseArray(json, clazz);
        } catch (Exception e) {
            log.error("JSON字符串转List失败，json: {}", json, e);
            throw new RuntimeException("JSON字符串转List失败", e);
        }
    }

    /**
     * JSON字符串转JSONObject
     */
    public static JSONObject parseObject(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON.parseObject(json);
        } catch (Exception e) {
            log.error("JSON解析失败，json: {}", json, e);
            throw new RuntimeException("JSON解析失败", e);
        }
    }

    /**
     * JSON字符串转JSONArray
     */
    public static JSONArray parseArray(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON.parseArray(json);
        } catch (Exception e) {
            log.error("JSON解析失败，json: {}", json, e);
            throw new RuntimeException("JSON解析失败", e);
        }
    }

    /**
     * JSON字符串转Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseMap(String json) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return JSON.parseObject(json, Map.class);
        } catch (Exception e) {
            log.error("JSON字符串转Map失败，json: {}", json, e);
            throw new RuntimeException("JSON字符串转Map失败", e);
        }
    }

    /**
     * 对象转Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> objectToMap(Object object) {
        if (object == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(object), Map.class);
    }

    /**
     * Map转对象
     */
    public static <T> T mapToObject(Map<?, ?> map, Class<T> clazz) {
        if (map == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(map), clazz);
    }

    /**
     * 对象深拷贝
     */
    public static <T> T deepCopy(Object object, Class<T> clazz) {
        if (object == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(object), clazz);
    }

    /**
     * 判断是否为有效的JSON字符串
     */
    public static boolean isValidJson(String json) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            JSON.parse(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否为JSON对象
     */
    public static boolean isJsonObject(String json) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            JSON.parseObject(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断是否为JSON数组
     */
    public static boolean isJsonArray(String json) {
        if (StringUtils.isEmpty(json)) {
            return false;
        }
        try {
            JSON.parseArray(json);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取JSON中指定路径的值
     */
    public static Object getValueByPath(String json, String path) {
        if (StringUtils.isEmpty(json) || StringUtils.isEmpty(path)) {
            return null;
        }
        try {
            JSONObject jsonObject = JSON.parseObject(json);
            String[] paths = path.split("\\.");
            Object result = jsonObject;
            for (String p : paths) {
                if (result instanceof JSONObject) {
                    result = ((JSONObject) result).get(p);
                } else {
                    return null;
                }
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取JSON中指定路径的String值
     */
    public static String getStringByPath(String json, String path) {
        Object value = getValueByPath(json, path);
        return value != null ? value.toString() : null;
    }
}
