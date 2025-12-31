package com.aeye.mifss.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 身份证工具类
 *
 * @author mifss
 */
public class IdcardUtils {

    /** 加权因子 */
    private static final int[] WEIGHT = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

    /** 校验码 */
    private static final char[] CHECK_CODE = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };

    /** 省份代码 */
    private static final Map<String, String> PROVINCE_MAP = new HashMap<>();

    static {
        PROVINCE_MAP.put("11", "北京");
        PROVINCE_MAP.put("12", "天津");
        PROVINCE_MAP.put("13", "河北");
        PROVINCE_MAP.put("14", "山西");
        PROVINCE_MAP.put("15", "内蒙古");
        PROVINCE_MAP.put("21", "辽宁");
        PROVINCE_MAP.put("22", "吉林");
        PROVINCE_MAP.put("23", "黑龙江");
        PROVINCE_MAP.put("31", "上海");
        PROVINCE_MAP.put("32", "江苏");
        PROVINCE_MAP.put("33", "浙江");
        PROVINCE_MAP.put("34", "安徽");
        PROVINCE_MAP.put("35", "福建");
        PROVINCE_MAP.put("36", "江西");
        PROVINCE_MAP.put("37", "山东");
        PROVINCE_MAP.put("41", "河南");
        PROVINCE_MAP.put("42", "湖北");
        PROVINCE_MAP.put("43", "湖南");
        PROVINCE_MAP.put("44", "广东");
        PROVINCE_MAP.put("45", "广西");
        PROVINCE_MAP.put("46", "海南");
        PROVINCE_MAP.put("50", "重庆");
        PROVINCE_MAP.put("51", "四川");
        PROVINCE_MAP.put("52", "贵州");
        PROVINCE_MAP.put("53", "云南");
        PROVINCE_MAP.put("54", "西藏");
        PROVINCE_MAP.put("61", "陕西");
        PROVINCE_MAP.put("62", "甘肃");
        PROVINCE_MAP.put("63", "青海");
        PROVINCE_MAP.put("64", "宁夏");
        PROVINCE_MAP.put("65", "新疆");
        PROVINCE_MAP.put("71", "台湾");
        PROVINCE_MAP.put("81", "香港");
        PROVINCE_MAP.put("82", "澳门");
    }

    /**
     * 验证身份证号是否有效
     */
    public static boolean isValid(String idCard) {
        if (StringUtils.isEmpty(idCard)) {
            return false;
        }
        idCard = idCard.trim();
        if (idCard.length() == 15) {
            return isValid15(idCard);
        } else if (idCard.length() == 18) {
            return isValid18(idCard);
        }
        return false;
    }

    /**
     * 验证15位身份证号
     */
    private static boolean isValid15(String idCard) {
        if (!idCard.matches("^\\d{15}$")) {
            return false;
        }
        // 验证省份
        if (!PROVINCE_MAP.containsKey(idCard.substring(0, 2))) {
            return false;
        }
        // 验证生日
        String birthday = "19" + idCard.substring(6, 12);
        return isValidDate(birthday, "yyyyMMdd");
    }

    /**
     * 验证18位身份证号
     */
    private static boolean isValid18(String idCard) {
        if (!idCard.matches("^\\d{17}[0-9Xx]$")) {
            return false;
        }
        // 验证省份
        if (!PROVINCE_MAP.containsKey(idCard.substring(0, 2))) {
            return false;
        }
        // 验证生日
        String birthday = idCard.substring(6, 14);
        if (!isValidDate(birthday, "yyyyMMdd")) {
            return false;
        }
        // 验证校验码
        return validateCheckCode(idCard);
    }

    /**
     * 验证校验码
     */
    private static boolean validateCheckCode(String idCard) {
        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (idCard.charAt(i) - '0') * WEIGHT[i];
        }
        char checkCode = CHECK_CODE[sum % 11];
        return Character.toUpperCase(idCard.charAt(17)) == checkCode;
    }

    /**
     * 验证日期格式
     */
    private static boolean isValidDate(String date, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            sdf.setLenient(false);
            sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 获取出生日期
     */
    public static Date getBirthday(String idCard) {
        if (!isValid(idCard)) {
            return null;
        }
        String birthday;
        if (idCard.length() == 15) {
            birthday = "19" + idCard.substring(6, 12);
        } else {
            birthday = idCard.substring(6, 14);
        }
        try {
            return new SimpleDateFormat("yyyyMMdd").parse(birthday);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取出生日期字符串（yyyy-MM-dd）
     */
    public static String getBirthdayStr(String idCard) {
        Date birthday = getBirthday(idCard);
        if (birthday == null) {
            return null;
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(birthday);
    }

    /**
     * 获取年龄
     */
    public static int getAge(String idCard) {
        Date birthday = getBirthday(idCard);
        if (birthday == null) {
            return -1;
        }
        Calendar cal = Calendar.getInstance();
        int nowYear = cal.get(Calendar.YEAR);
        cal.setTime(birthday);
        int birthYear = cal.get(Calendar.YEAR);
        return nowYear - birthYear;
    }

    /**
     * 获取性别（1-男，2-女）
     */
    public static int getGender(String idCard) {
        if (!isValid(idCard)) {
            return -1;
        }
        int genderCode;
        if (idCard.length() == 15) {
            genderCode = idCard.charAt(14) - '0';
        } else {
            genderCode = idCard.charAt(16) - '0';
        }
        return (genderCode % 2 == 0) ? 2 : 1;
    }

    /**
     * 获取性别描述
     */
    public static String getGenderDesc(String idCard) {
        int gender = getGender(idCard);
        if (gender == 1)
            return "男";
        if (gender == 2)
            return "女";
        return "未知";
    }

    /**
     * 获取省份
     */
    public static String getProvince(String idCard) {
        if (!isValid(idCard)) {
            return null;
        }
        return PROVINCE_MAP.get(idCard.substring(0, 2));
    }

    /**
     * 15位身份证转18位
     */
    public static String convert15To18(String idCard) {
        if (idCard == null || idCard.length() != 15) {
            return null;
        }
        if (!isValid15(idCard)) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(idCard, 0, 6);
        sb.append("19");
        sb.append(idCard.substring(6));

        int sum = 0;
        for (int i = 0; i < 17; i++) {
            sum += (sb.charAt(i) - '0') * WEIGHT[i];
        }
        sb.append(CHECK_CODE[sum % 11]);
        return sb.toString();
    }

    /**
     * 隐藏身份证号中间部分
     */
    public static String hide(String idCard) {
        if (StringUtils.isEmpty(idCard) || idCard.length() < 8) {
            return idCard;
        }
        return idCard.substring(0, 4) + "**********" + idCard.substring(idCard.length() - 4);
    }
}
