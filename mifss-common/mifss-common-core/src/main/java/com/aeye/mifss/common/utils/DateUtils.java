package com.aeye.mifss.common.utils;

import java.lang.management.ManagementFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * 日期时间工具类
 *
 * @author mifss
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static final String YYYY = "yyyy";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    private static final String[] PARSE_PATTERNS = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"
    };

    /**
     * 获取当前Date型日期
     *
     * @return Date 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期字符串，格式：yyyy-MM-dd
     *
     * @return String 当前日期字符串
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    /**
     * 获取当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return String 当前时间字符串
     */
    public static String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取当前日期时间字符串，格式：yyyyMMddHHmmss
     *
     * @return String 当前日期时间字符串
     */
    public static String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    /**
     * 获取当前日期时间字符串
     *
     * @param format 格式
     * @return String 当前日期时间字符串
     */
    public static String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    /**
     * 获取指定日期的日期字符串，格式：yyyy-MM-dd
     *
     * @param date 日期
     * @return String 日期字符串
     */
    public static String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    /**
     * 日期格式转字符串
     *
     * @param format 格式
     * @param date   日期
     * @return String 日期字符串
     */
    public static String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 字符串转日期
     *
     * @param format 格式
     * @param ts     日期字符串
     * @return Date 日期
     */
    public static Date dateTime(final String format, final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径，用于按日期分类文件
     * 例如：2024/01/01
     *
     * @return String 日期路径
     */
    public static String datePath() {
        Date now = new Date();
        return parseDateToStr("yyyy/MM/dd", now);
    }

    /**
     * 日期路径，用于按日期分类文件
     * 例如：20240101
     *
     * @return String 日期路径
     */
    public static String dateTime() {
        Date now = new Date();
        return parseDateToStr("yyyyMMdd", now);
    }

    /**
     * 日期型字符串转化为日期（自动匹配多种格式）
     *
     * @param str 日期字符串
     * @return Date 日期
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), PARSE_PATTERNS);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取服务器启动时间
     *
     * @return Date 服务器启动时间
     */
    public static Date getServerStartDate() {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 计算两个时间差
     *
     * @param endDate   结束时间
     * @param startDate 开始时间
     * @return String 时间差描述
     */
    public static String timeDistance(Date endDate, Date startDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        long diff = endDate.getTime() - startDate.getTime();
        long day = diff / nd;
        long hour = diff % nd / nh;
        long min = diff % nd % nh / nm;
        long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
    }

    /**
     * 计算两个日期之间的天数
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return long 天数差
     */
    public static long differentDaysByMillisecond(Date date1, Date date2) {
        return Math.abs((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
    }

    /**
     * 获取今天开始时间
     *
     * @return Date 今天开始时间
     */
    public static Date getTodayStart() {
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        return Date.from(todayStart.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 获取今天结束时间
     *
     * @return Date 今天结束时间
     */
    public static Date getTodayEnd() {
        LocalDateTime todayEnd = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        return Date.from(todayEnd.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Date转LocalDateTime
     *
     * @param date Date日期
     * @return LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * LocalDateTime转Date
     *
     * @param localDateTime LocalDateTime日期
     * @return Date
     */
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 增加天数
     *
     * @param date 日期
     * @param days 天数
     * @return Date 增加后的日期
     */
    public static Date addDays(Date date, int days) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.plusDays(days));
    }

    /**
     * 增加小时
     *
     * @param date  日期
     * @param hours 小时数
     * @return Date 增加后的日期
     */
    public static Date addHours(Date date, int hours) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.plusHours(hours));
    }

    /**
     * 增加分钟
     *
     * @param date    日期
     * @param minutes 分钟数
     * @return Date 增加后的日期
     */
    public static Date addMinutes(Date date, int minutes) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return toDate(localDateTime.plusMinutes(minutes));
    }

    /**
     * 获取当前时间戳（秒）
     *
     * @return long 时间戳
     */
    public static long getTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取当前时间戳（毫秒）
     *
     * @return long 时间戳
     */
    public static long getTimestampMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 判断是否为同一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return boolean 是否同一天
     */
    public static boolean isSameDay(Date date1, Date date2) {
        LocalDate localDate1 = toLocalDateTime(date1).toLocalDate();
        LocalDate localDate2 = toLocalDateTime(date2).toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    /**
     * 计算年龄
     *
     * @param birthDate 出生日期
     * @return int 年龄
     */
    public static int getAge(Date birthDate) {
        LocalDate birth = toLocalDateTime(birthDate).toLocalDate();
        LocalDate now = LocalDate.now();
        return (int) ChronoUnit.YEARS.between(birth, now);
    }
}
