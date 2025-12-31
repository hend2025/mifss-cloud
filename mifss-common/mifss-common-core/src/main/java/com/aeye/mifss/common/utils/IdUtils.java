package com.aeye.mifss.common.utils;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * ID生成工具类
 *
 * @author mifss
 */
public class IdUtils {

    /**
     * 雪花算法单例
     */
    private static final Snowflake SNOWFLAKE = new Snowflake();

    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String randomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取简化的UUID（去掉横线）
     *
     * @return 简化的UUID
     */
    public static String simpleUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取雪花算法ID
     *
     * @return 雪花算法ID
     */
    public static long snowflakeId() {
        return SNOWFLAKE.nextId();
    }

    /**
     * 获取雪花算法ID（字符串形式）
     *
     * @return 雪花算法ID字符串
     */
    public static String snowflakeIdStr() {
        return String.valueOf(SNOWFLAKE.nextId());
    }

    /**
     * 获取随机数字字符串
     *
     * @param length 长度
     * @return 随机数字字符串
     */
    public static String randomNumbers(int length) {
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 获取随机字母数字字符串
     *
     * @param length 长度
     * @return 随机字母数字字符串
     */
    public static String randomAlphanumeric(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * 生成订单号
     * 格式：时间戳 + 随机数
     *
     * @return 订单号
     */
    public static String generateOrderNo() {
        return DateUtils.dateTimeNow() + randomNumbers(6);
    }

    /**
     * 雪花算法实现
     */
    private static class Snowflake {
        /**
         * 起始的时间戳 (2020-01-01 00:00:00)
         */
        private static final long START_TIMESTAMP = 1577808000000L;

        /**
         * 序列号占用的位数
         */
        private static final long SEQUENCE_BITS = 12;

        /**
         * 机器标识占用的位数
         */
        private static final long WORKER_ID_BITS = 5;

        /**
         * 数据中心占用的位数
         */
        private static final long DATA_CENTER_ID_BITS = 5;

        /**
         * 序列号最大值
         */
        private static final long MAX_SEQUENCE = ~(-1L << SEQUENCE_BITS);

        /**
         * 机器ID向左移12位
         */
        private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

        /**
         * 数据中心ID向左移17位
         */
        private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

        /**
         * 时间戳向左移22位
         */
        private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;

        /**
         * 机器ID (0~31)
         */
        private final long workerId;

        /**
         * 数据中心ID (0~31)
         */
        private final long dataCenterId;

        /**
         * 序列号
         */
        private long sequence = 0L;

        /**
         * 上次生成ID的时间戳
         */
        private long lastTimestamp = -1L;

        public Snowflake() {
            this(ThreadLocalRandom.current().nextInt(32), ThreadLocalRandom.current().nextInt(32));
        }

        public Snowflake(long workerId, long dataCenterId) {
            long maxWorkerId = ~(-1L << WORKER_ID_BITS);
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(
                        "Worker ID can't be greater than " + maxWorkerId + " or less than 0");
            }
            long maxDataCenterId = ~(-1L << DATA_CENTER_ID_BITS);
            if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
                throw new IllegalArgumentException(
                        "DataCenter ID can't be greater than " + maxDataCenterId + " or less than 0");
            }
            this.workerId = workerId;
            this.dataCenterId = dataCenterId;
        }

        public synchronized long nextId() {
            long currentTimestamp = System.currentTimeMillis();

            if (currentTimestamp < lastTimestamp) {
                throw new RuntimeException("Clock moved backwards. Refusing to generate id");
            }

            if (lastTimestamp == currentTimestamp) {
                sequence = (sequence + 1) & MAX_SEQUENCE;
                if (sequence == 0) {
                    currentTimestamp = waitNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0L;
            }

            lastTimestamp = currentTimestamp;

            return ((currentTimestamp - START_TIMESTAMP) << TIMESTAMP_SHIFT)
                    | (dataCenterId << DATA_CENTER_ID_SHIFT)
                    | (workerId << WORKER_ID_SHIFT)
                    | sequence;
        }

        private long waitNextMillis(long lastTimestamp) {
            long timestamp = System.currentTimeMillis();
            while (timestamp <= lastTimestamp) {
                timestamp = System.currentTimeMillis();
            }
            return timestamp;
        }
    }
}
