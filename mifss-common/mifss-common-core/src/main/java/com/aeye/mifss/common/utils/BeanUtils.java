package com.aeye.mifss.common.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Bean工具类
 * 基于Hutool BeanUtil实现
 *
 * @author mifss
 */
public class BeanUtils {

    private static final Logger log = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 复制Bean属性
     */
    public static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        BeanUtil.copyProperties(source, target);
    }

    /**
     * 复制Bean属性（忽略指定属性）
     */
    public static void copyProperties(Object source, Object target, String... ignoreProperties) {
        if (source == null || target == null) {
            return;
        }
        CopyOptions options = CopyOptions.create().setIgnoreProperties(ignoreProperties);
        BeanUtil.copyProperties(source, target, options);
    }

    /**
     * 复制Bean属性（忽略null值）
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        CopyOptions options = CopyOptions.create().ignoreNullValue();
        BeanUtil.copyProperties(source, target, options);
    }

    /**
     * 复制对象并转换类型
     */
    public static <T> T copyObject(Object source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }
        try {
            return BeanUtil.copyProperties(source, targetClass);
        } catch (Exception e) {
            log.error("复制对象并转换类型失败", e);
            throw new RuntimeException("复制对象并转换类型失败", e);
        }
    }

    /**
     * 复制List并转换类型
     */
    public static <S, T> List<T> copyList(List<S> sourceList, Class<T> targetClass) {
        if (sourceList == null) {
            return null;
        }
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (S source : sourceList) {
            targetList.add(copyObject(source, targetClass));
        }
        return targetList;
    }

    /**
     * Bean转Map
     */
    public static Map<String, Object> beanToMap(Object bean) {
        if (bean == null) {
            return null;
        }
        return BeanUtil.beanToMap(bean);
    }

    /**
     * Bean转Map（忽略null值）
     */
    public static Map<String, Object> beanToMapIgnoreNull(Object bean) {
        if (bean == null) {
            return null;
        }
        return BeanUtil.beanToMap(bean, false, true);
    }

    /**
     * Map转Bean
     */
    public static <T> T mapToBean(Map<?, ?> map, Class<T> beanClass) {
        if (map == null) {
            return null;
        }
        return BeanUtil.mapToBean(map, beanClass, true, null);
    }

    /**
     * 填充Bean属性（从Map）
     */
    public static void fillBeanWithMap(Map<?, ?> map, Object bean) {
        if (map == null || bean == null) {
            return;
        }
        BeanUtil.fillBeanWithMap(map, bean, true);
    }

    /**
     * 获取对象属性值
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        if (obj == null || StringUtils.isEmpty(fieldName)) {
            return null;
        }
        return BeanUtil.getFieldValue(obj, fieldName);
    }

    /**
     * 设置对象属性值
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) {
        if (obj == null || StringUtils.isEmpty(fieldName)) {
            return;
        }
        BeanUtil.setFieldValue(obj, fieldName, value);
    }

    /**
     * 判断是否为Bean类型
     */
    public static boolean isBean(Class<?> clazz) {
        return BeanUtil.isBean(clazz);
    }

    /**
     * 判断Bean是否为空（所有属性都为null）
     */
    public static boolean isEmpty(Object bean) {
        if (bean == null) {
            return true;
        }
        return BeanUtil.isEmpty(bean, (String[]) null);
    }

    /**
     * 判断Bean是否非空
     */
    public static boolean isNotEmpty(Object bean) {
        return !isEmpty(bean);
    }
}
