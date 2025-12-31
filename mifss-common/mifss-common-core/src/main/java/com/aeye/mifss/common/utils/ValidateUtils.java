package com.aeye.mifss.common.utils;

import java.util.regex.Pattern;

/**
 * 正则校验工具类
 *
 * @author mifss
 */
public class ValidateUtils {

    private static final Pattern MOBILE_PATTERN = Pattern.compile("^1[3-9]\\d{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern ID_CARD_PATTERN = Pattern
            .compile("^[1-9]\\d{5}(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$");
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?|ftp)://[^\\s/$.?#].[^\\s]*$");
    private static final Pattern IP_PATTERN = Pattern
            .compile("^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$");
    private static final Pattern CHINESE_PATTERN = Pattern.compile("^[\\u4e00-\\u9fa5]+$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z][a-zA-Z0-9_]{4,15}$");

    /**
     * 验证手机号码
     */
    public static boolean isMobile(String mobile) {
        return isMatch(MOBILE_PATTERN, mobile);
    }

    /**
     * 验证邮箱
     */
    public static boolean isEmail(String email) {
        return isMatch(EMAIL_PATTERN, email);
    }

    /**
     * 验证身份证号
     */
    public static boolean isIdCard(String idCard) {
        return isMatch(ID_CARD_PATTERN, idCard);
    }

    /**
     * 验证URL
     */
    public static boolean isUrl(String url) {
        return isMatch(URL_PATTERN, url);
    }

    /**
     * 验证IP地址
     */
    public static boolean isIp(String ip) {
        return isMatch(IP_PATTERN, ip);
    }

    /**
     * 验证中文
     */
    public static boolean isChinese(String str) {
        return isMatch(CHINESE_PATTERN, str);
    }

    /**
     * 验证用户名（字母开头，5-16位字母数字下划线）
     */
    public static boolean isUsername(String username) {
        return isMatch(USERNAME_PATTERN, username);
    }

    /**
     * 验证是否为纯数字
     */
    public static boolean isNumeric(String str) {
        if (StringUtils.isEmpty(str))
            return false;
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return true;
    }

    /**
     * 验证是否为字母
     */
    public static boolean isAlpha(String str) {
        if (StringUtils.isEmpty(str))
            return false;
        for (char c : str.toCharArray()) {
            if (!Character.isLetter(c))
                return false;
        }
        return true;
    }

    /**
     * 验证字符串长度
     */
    public static boolean isLengthBetween(String str, int min, int max) {
        if (str == null)
            return false;
        int len = str.length();
        return len >= min && len <= max;
    }

    private static boolean isMatch(Pattern pattern, String str) {
        return StringUtils.isNotEmpty(str) && pattern.matcher(str).matches();
    }
}
