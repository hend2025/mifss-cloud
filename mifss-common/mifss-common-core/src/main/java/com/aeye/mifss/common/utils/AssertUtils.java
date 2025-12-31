package com.aeye.mifss.common.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 断言工具类
 *
 * @author mifss
 */
public class AssertUtils {

    /**
     * 断言表达式为true
     */
    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言表达式为false
     */
    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言对象不为null
     */
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言对象为null
     */
    public static void isNull(Object object, String message) {
        if (object != null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言字符串不为空
     */
    public static void notEmpty(String str, String message) {
        if (StringUtils.isEmpty(str)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言集合不为空
     */
    public static void notEmpty(Collection<?> collection, String message) {
        if (StringUtils.isEmpty(collection)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言Map不为空
     */
    public static void notEmpty(Map<?, ?> map, String message) {
        if (StringUtils.isEmpty(map)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言数组不为空
     */
    public static void notEmpty(Object[] array, String message) {
        if (StringUtils.isEmpty(array)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言两个对象相等
     */
    public static void equals(Object obj1, Object obj2, String message) {
        if (obj1 == null && obj2 == null)
            return;
        if (obj1 == null || !obj1.equals(obj2)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言两个对象不相等
     */
    public static void notEquals(Object obj1, Object obj2, String message) {
        if (obj1 == null && obj2 == null) {
            throw new IllegalArgumentException(message);
        }
        if (obj1 != null && obj1.equals(obj2)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言字符串长度在指定范围内
     */
    public static void length(String str, int min, int max, String message) {
        int len = str == null ? 0 : str.length();
        if (len < min || len > max) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 断言数值在指定范围内
     */
    public static void range(long value, long min, long max, String message) {
        if (value < min || value > max) {
            throw new IllegalArgumentException(message);
        }
    }
}
