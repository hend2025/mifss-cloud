package com.aeye.mifss.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Base64编解码工具类
 *
 * @author mifss
 */
public class Base64Utils {

    private static final Logger log = LoggerFactory.getLogger(Base64Utils.class);

    /**
     * 标准Base64编码
     */
    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 标准Base64解码
     */
    public static String decode(String base64) {
        if (StringUtils.isEmpty(base64)) {
            return null;
        }
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            log.error("Base64解码失败", e);
            return null;
        }
    }

    /**
     * 字节数组Base64编码
     */
    public static String encodeBytes(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Base64解码为字节数组
     */
    public static byte[] decodeToBytes(String base64) {
        if (StringUtils.isEmpty(base64)) {
            return null;
        }
        try {
            return Base64.getDecoder().decode(base64);
        } catch (IllegalArgumentException e) {
            log.error("Base64解码失败", e);
            return null;
        }
    }

    /**
     * URL安全的Base64编码
     */
    public static String encodeUrlSafe(String str) {
        if (str == null) {
            return null;
        }
        return Base64.getUrlEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * URL安全的Base64解码
     */
    public static String decodeUrlSafe(String base64) {
        if (StringUtils.isEmpty(base64)) {
            return null;
        }
        try {
            byte[] bytes = Base64.getUrlDecoder().decode(base64);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            log.error("Base64 URL解码失败", e);
            return null;
        }
    }

    /**
     * MIME格式Base64编码（每76个字符换行）
     */
    public static String encodeMime(String str) {
        if (str == null) {
            return null;
        }
        return Base64.getMimeEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * MIME格式Base64解码
     */
    public static String decodeMime(String base64) {
        if (StringUtils.isEmpty(base64)) {
            return null;
        }
        try {
            byte[] bytes = Base64.getMimeDecoder().decode(base64);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            log.error("Base64 MIME解码失败", e);
            return null;
        }
    }

    /**
     * 文件转Base64
     */
    public static String fileToBase64(String filePath) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(filePath));
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            log.error("文件转Base64失败: {}", filePath, e);
            return null;
        }
    }

    /**
     * Base64转文件
     */
    public static boolean base64ToFile(String base64, String filePath) {
        try {
            byte[] bytes = Base64.getDecoder().decode(base64);
            Files.write(Paths.get(filePath), bytes);
            return true;
        } catch (Exception e) {
            log.error("Base64转文件失败: {}", filePath, e);
            return false;
        }
    }

    /**
     * 输入流转Base64
     */
    public static String inputStreamToBase64(InputStream inputStream) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int len;
            while ((len = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            log.error("输入流转Base64失败", e);
            return null;
        }
    }

    /**
     * 判断是否为有效的Base64字符串
     */
    public static boolean isBase64(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        try {
            Base64.getDecoder().decode(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * 图片转Data URI格式
     * 
     * @param filePath 图片路径
     * @param mimeType MIME类型（如 image/png, image/jpeg）
     */
    public static String imageToDataUri(String filePath, String mimeType) {
        String base64 = fileToBase64(filePath);
        if (base64 == null) {
            return null;
        }
        return "data:" + mimeType + ";base64," + base64;
    }

    /**
     * 从Data URI中提取Base64内容
     */
    public static String extractBase64FromDataUri(String dataUri) {
        if (StringUtils.isEmpty(dataUri) || !dataUri.contains(",")) {
            return null;
        }
        return dataUri.substring(dataUri.indexOf(",") + 1);
    }
}
