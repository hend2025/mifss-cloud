package com.aeye.mifss.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 *
 * @author mifss
 */
public class Md5Utils {

    private static final Logger log = LoggerFactory.getLogger(Md5Utils.class);

    /**
     * 十六进制字符数组
     */
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f' };

    /**
     * MD5加密（32位小写）
     *
     * @param str 待加密字符串
     * @return 加密后的字符串
     */
    public static String md5(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5加密失败", e);
            throw new RuntimeException("MD5加密失败", e);
        }
    }

    /**
     * MD5加密（32位大写）
     *
     * @param str 待加密字符串
     * @return 加密后的字符串（大写）
     */
    public static String md5Upper(String str) {
        String result = md5(str);
        return result != null ? result.toUpperCase() : null;
    }

    /**
     * MD5加密（16位小写）
     *
     * @param str 待加密字符串
     * @return 加密后的字符串（16位）
     */
    public static String md5Short(String str) {
        String result = md5(str);
        return result != null ? result.substring(8, 24) : null;
    }

    /**
     * MD5加盐加密
     *
     * @param str  待加密字符串
     * @param salt 盐值
     * @return 加密后的字符串
     */
    public static String md5WithSalt(String str, String salt) {
        if (str == null) {
            return null;
        }
        return md5(str + salt);
    }

    /**
     * 双重MD5加密
     *
     * @param str 待加密字符串
     * @return 加密后的字符串
     */
    public static String doubleMd5(String str) {
        return md5(md5(str));
    }

    /**
     * 对字节数组进行MD5加密
     *
     * @param bytes 字节数组
     * @return 加密后的字符串
     */
    public static String md5(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytes);
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5加密失败", e);
            throw new RuntimeException("MD5加密失败", e);
        }
    }

    /**
     * 验证MD5
     *
     * @param str     原始字符串
     * @param md5Hash MD5哈希值
     * @return 是否匹配
     */
    public static boolean verify(String str, String md5Hash) {
        if (str == null || md5Hash == null) {
            return false;
        }
        return md5(str).equalsIgnoreCase(md5Hash);
    }

    /**
     * 验证MD5（带盐）
     *
     * @param str     原始字符串
     * @param salt    盐值
     * @param md5Hash MD5哈希值
     * @return 是否匹配
     */
    public static boolean verifyWithSalt(String str, String salt, String md5Hash) {
        if (str == null || md5Hash == null) {
            return false;
        }
        return md5WithSalt(str, salt).equalsIgnoreCase(md5Hash);
    }

    /**
     * 字节数组转十六进制字符串
     */
    private static String bytesToHex(byte[] bytes) {
        char[] result = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            result[i * 2] = HEX_DIGITS[(bytes[i] >> 4) & 0x0F];
            result[i * 2 + 1] = HEX_DIGITS[bytes[i] & 0x0F];
        }
        return new String(result);
    }
}
