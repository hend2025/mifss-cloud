package com.aeye.mifss.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES加密解密工具类
 *
 * @author mifss
 */
public class AesUtils {

    private static final Logger log = LoggerFactory.getLogger(AesUtils.class);

    private static final String AES = "AES";
    private static final String AES_ECB = "AES/ECB/PKCS5Padding";
    private static final String AES_CBC = "AES/CBC/PKCS5Padding";
    private static final String AES_GCM = "AES/GCM/NoPadding";

    private static final int GCM_TAG_LENGTH = 128;
    private static final int GCM_IV_LENGTH = 12;
    private static final int CBC_IV_LENGTH = 16;

    /**
     * 生成AES密钥（128位）
     */
    public static String generateKey() {
        return generateKey(128);
    }

    /**
     * 生成AES密钥
     * 
     * @param keySize 密钥长度（128, 192, 256）
     */
    public static String generateKey(int keySize) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(AES);
            keyGen.init(keySize, new SecureRandom());
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            log.error("生成AES密钥失败", e);
            throw new RuntimeException("生成AES密钥失败", e);
        }
    }

    /**
     * 生成随机IV
     */
    public static String generateIv() {
        byte[] iv = new byte[CBC_IV_LENGTH];
        new SecureRandom().nextBytes(iv);
        return Base64.getEncoder().encodeToString(iv);
    }

    // ========================== ECB模式 ==========================

    /**
     * AES ECB模式加密
     */
    public static String encryptEcb(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ECB);
            SecretKeySpec keySpec = new SecretKeySpec(getKeyBytes(key), AES);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("AES ECB加密失败", e);
            throw new RuntimeException("AES ECB加密失败", e);
        }
    }

    /**
     * AES ECB模式解密
     */
    public static String decryptEcb(String content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ECB);
            SecretKeySpec keySpec = new SecretKeySpec(getKeyBytes(key), AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("AES ECB解密失败", e);
            throw new RuntimeException("AES ECB解密失败", e);
        }
    }

    // ========================== CBC模式 ==========================

    /**
     * AES CBC模式加密
     */
    public static String encryptCbc(String content, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC);
            SecretKeySpec keySpec = new SecretKeySpec(getKeyBytes(key), AES);
            IvParameterSpec ivSpec = new IvParameterSpec(getIvBytes(iv));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("AES CBC加密失败", e);
            throw new RuntimeException("AES CBC加密失败", e);
        }
    }

    /**
     * AES CBC模式解密
     */
    public static String decryptCbc(String content, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance(AES_CBC);
            SecretKeySpec keySpec = new SecretKeySpec(getKeyBytes(key), AES);
            IvParameterSpec ivSpec = new IvParameterSpec(getIvBytes(iv));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("AES CBC解密失败", e);
            throw new RuntimeException("AES CBC解密失败", e);
        }
    }

    // ========================== GCM模式（推荐） ==========================

    /**
     * AES GCM模式加密（最安全，推荐使用）
     * 返回格式：Base64(IV + 密文 + Tag)
     */
    public static String encryptGcm(String content, String key) {
        try {
            byte[] iv = new byte[GCM_IV_LENGTH];
            new SecureRandom().nextBytes(iv);

            Cipher cipher = Cipher.getInstance(AES_GCM);
            SecretKeySpec keySpec = new SecretKeySpec(getKeyBytes(key), AES);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));

            // 将IV和密文拼接
            byte[] result = new byte[iv.length + encrypted.length];
            System.arraycopy(iv, 0, result, 0, iv.length);
            System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);

            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            log.error("AES GCM加密失败", e);
            throw new RuntimeException("AES GCM加密失败", e);
        }
    }

    /**
     * AES GCM模式解密
     */
    public static String decryptGcm(String content, String key) {
        try {
            byte[] data = Base64.getDecoder().decode(content);

            // 提取IV
            byte[] iv = new byte[GCM_IV_LENGTH];
            System.arraycopy(data, 0, iv, 0, iv.length);

            // 提取密文
            byte[] encrypted = new byte[data.length - iv.length];
            System.arraycopy(data, iv.length, encrypted, 0, encrypted.length);

            Cipher cipher = Cipher.getInstance(AES_GCM);
            SecretKeySpec keySpec = new SecretKeySpec(getKeyBytes(key), AES);
            GCMParameterSpec gcmSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);
            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("AES GCM解密失败", e);
            throw new RuntimeException("AES GCM解密失败", e);
        }
    }

    // ========================== 简化方法 ==========================

    /**
     * AES加密（默认使用GCM模式）
     */
    public static String encrypt(String content, String key) {
        return encryptGcm(content, key);
    }

    /**
     * AES解密（默认使用GCM模式）
     */
    public static String decrypt(String content, String key) {
        return decryptGcm(content, key);
    }

    /**
     * 字节数组加密
     */
    public static byte[] encryptBytes(byte[] content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ECB);
            SecretKeySpec keySpec = new SecretKeySpec(getKeyBytes(key), AES);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            return cipher.doFinal(content);
        } catch (Exception e) {
            log.error("AES加密失败", e);
            throw new RuntimeException("AES加密失败", e);
        }
    }

    /**
     * 字节数组解密
     */
    public static byte[] decryptBytes(byte[] content, String key) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ECB);
            SecretKeySpec keySpec = new SecretKeySpec(getKeyBytes(key), AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            return cipher.doFinal(content);
        } catch (Exception e) {
            log.error("AES解密失败", e);
            throw new RuntimeException("AES解密失败", e);
        }
    }

    /**
     * 获取密钥字节数组（支持Base64和普通字符串）
     */
    private static byte[] getKeyBytes(String key) {
        try {
            return Base64.getDecoder().decode(key);
        } catch (IllegalArgumentException e) {
            // 非Base64格式，补齐到16字节
            byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
            byte[] result = new byte[16];
            System.arraycopy(keyBytes, 0, result, 0, Math.min(keyBytes.length, 16));
            return result;
        }
    }

    /**
     * 获取IV字节数组
     */
    private static byte[] getIvBytes(String iv) {
        try {
            return Base64.getDecoder().decode(iv);
        } catch (IllegalArgumentException e) {
            byte[] ivBytes = iv.getBytes(StandardCharsets.UTF_8);
            byte[] result = new byte[CBC_IV_LENGTH];
            System.arraycopy(ivBytes, 0, result, 0, Math.min(ivBytes.length, CBC_IV_LENGTH));
            return result;
        }
    }
}
