package com.aeye.mifss.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 安全工具类
 *
 * @author mifss
 */
public class SecurityUtils {

    private static final Logger log = LoggerFactory.getLogger(SecurityUtils.class);

    /**
     * MD5加密
     */
    public static String md5(String str) {
        return hash(str, "MD5");
    }

    /**
     * SHA256加密
     */
    public static String sha256(String str) {
        return hash(str, "SHA-256");
    }

    private static String hash(String str, String algorithm) {
        if (str == null)
            return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] digest = md.digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            log.error("{}加密失败", algorithm, e);
            throw new RuntimeException(algorithm + "加密失败", e);
        }
    }

    /**
     * Base64编码
     */
    public static String base64Encode(String str) {
        if (str == null)
            return null;
        return Base64.getEncoder().encodeToString(str.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Base64解码
     */
    public static String base64Decode(String str) {
        if (str == null)
            return null;
        return new String(Base64.getDecoder().decode(str), StandardCharsets.UTF_8);
    }

    /**
     * AES加密
     */
    public static String aesEncrypt(String content, String key) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = keygen.generateKey();
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            log.error("AES加密失败", e);
            throw new RuntimeException("AES加密失败", e);
        }
    }

    /**
     * AES解密
     */
    public static String aesDecrypt(String content, String key) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom(key.getBytes()));
            SecretKey secretKey = keygen.generateKey();
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(content));
            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("AES解密失败", e);
            throw new RuntimeException("AES解密失败", e);
        }
    }

    /**
     * 脱敏手机号：138****8888
     */
    public static String maskPhone(String phone) {
        if (StringUtils.isEmpty(phone) || phone.length() != 11)
            return phone;
        return phone.substring(0, 3) + "****" + phone.substring(7);
    }

    /**
     * 脱敏身份证：110***********1234
     */
    public static String maskIdCard(String idCard) {
        if (StringUtils.isEmpty(idCard) || idCard.length() < 8)
            return idCard;
        return idCard.substring(0, 3) + "***********" + idCard.substring(idCard.length() - 4);
    }

    /**
     * 脱敏邮箱：a***@example.com
     */
    public static String maskEmail(String email) {
        if (StringUtils.isEmpty(email) || !email.contains("@"))
            return email;
        int atIndex = email.indexOf("@");
        if (atIndex <= 1)
            return email;
        return email.charAt(0) + "***" + email.substring(atIndex);
    }
}
