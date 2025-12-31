package com.aeye.mifss.common.utils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 *
 * @author mifss
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

    /**
     * 空字符串
     */
    private static final String EMPTY_STR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * 驼峰转下划线命名正则
     */
    private static final Pattern CAMEL_PATTERN = Pattern.compile("[A-Z]");

    /**
     * 下划线转驼峰命名正则
     */
    private static final Pattern UNDERSCORE_PATTERN = Pattern.compile("_(\\w)");

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return true=为空, false=非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || EMPTY_STR.equals(str.trim());
    }

    /**
     * 判断字符串是否非空
     *
     * @param str 字符串
     * @return true=非空, false=为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断对象是否为null
     *
     * @param object 对象
     * @return true=为null, false=非null
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 判断对象是否非null
     *
     * @param object 对象
     * @return true=非null, false=为null
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 判断集合是否为空
     *
     * @param coll 集合
     * @return true=为空, false=非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * 判断集合是否非空
     *
     * @param coll 集合
     * @return true=非空, false=为空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 判断数组是否为空
     *
     * @param objects 数组
     * @return true=为空, false=非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * 判断数组是否非空
     *
     * @param objects 数组
     * @return true=非空, false=为空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * 判断Map是否为空
     *
     * @param map Map对象
     * @return true=为空, false=非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * 判断Map是否非空
     *
     * @param map Map对象
     * @return true=非空, false=为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 去除字符串首尾空格
     *
     * @param str 字符串
     * @return 去除空格后的字符串
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始位置
     * @return 截取后的字符串
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return EMPTY_STR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return EMPTY_STR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始位置
     * @param end   结束位置
     * @return 截取后的字符串
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return EMPTY_STR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return EMPTY_STR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 格式化文本，使用 {} 作为占位符
     * 例如：format("hello {}, welcome to {}", "张三", "北京")
     * 
     * @param template 文本模板
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (isEmpty(template) || isEmpty(params)) {
            return template;
        }
        StringBuilder sb = new StringBuilder(template);
        for (Object param : params) {
            int index = sb.indexOf("{}");
            if (index == -1) {
                break;
            }
            sb.replace(index, index + 2, String.valueOf(param));
        }
        return sb.toString();
    }

    /**
     * 驼峰命名转下划线命名
     * 例如：userName -> user_name
     *
     * @param str 驼峰命名字符串
     * @return 下划线命名字符串
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = CAMEL_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, SEPARATOR + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 下划线命名转驼峰命名
     * 例如：user_name -> userName
     *
     * @param str 下划线命名字符串
     * @return 驼峰命名字符串
     */
    public static String toCamelCase(String str) {
        if (str == null) {
            return null;
        }
        str = str.toLowerCase();
        Matcher matcher = UNDERSCORE_PATTERN.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 首字母大写后的字符串
     */
    public static String capitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return 首字母小写后的字符串
     */
    public static String uncapitalize(String str) {
        if (isEmpty(str)) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 判断是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串数组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 将字符串转换为指定类型
     *
     * @param str   字符串
     * @param clazz 目标类型
     * @param <T>   泛型
     * @return 转换后的对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T convert(String str, Class<T> clazz) {
        if (str == null) {
            return null;
        }
        if (clazz == String.class) {
            return (T) str;
        } else if (clazz == Integer.class || clazz == int.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == Long.class || clazz == long.class) {
            return (T) Long.valueOf(str);
        } else if (clazz == Double.class || clazz == double.class) {
            return (T) Double.valueOf(str);
        } else if (clazz == Float.class || clazz == float.class) {
            return (T) Float.valueOf(str);
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            return (T) Boolean.valueOf(str);
        }
        return null;
    }

    /**
     * 获取参数不为空值
     *
     * @param value        要判断的值
     * @param defaultValue 默认值
     * @param <T>          泛型
     * @return 如果value为空，则返回defaultValue
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }
}
