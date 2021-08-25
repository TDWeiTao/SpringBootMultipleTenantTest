package com.springboot.mutilpletenant.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * <p>
 * 生成ID工具类
 * </p>
 *
 * @author bo.zhang
 * @since 2021-08-25
 */
public class IdWorker {

    /** 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动） */
    private final static long TWEPOCH = 1288834974657L;
    /** 机器标识位数 */
    private final static long WORKER_ID_BITS = 5L;
    /** 数据中心标识位数 */
    private final static long DATACENTER_ID_BITS = 5L;
    /** 机器ID最大值 */
    private final static long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    /** 数据中心ID最大值 */
    private final static long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);
    /** 毫秒内自增位 */
    private final static long SEQUENCE_BITS = 12L;
    /** 机器ID偏左移12位 */
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    /** 数据中心ID左移17位 */
    private final static long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    /** 时间毫秒左移22位 */
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

    private final static long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    /** 上次生产id时间戳 */
    private static long LAST_TIMESTAMP = -1L;
    /** 0，并发控制 */
    private static long SEQUENCE = 0L;

    private static long WORK_ID = 0L;
     /** 数据标识id部分 */
    private static long DATACENTER_ID = 0L;

    public IdWorker() {
        DATACENTER_ID = getDatacenterId(MAX_DATACENTER_ID);
        WORK_ID = getMaxWorkerId(DATACENTER_ID, MAX_WORKER_ID);
    }

    /**
     * @param workerId     工作机器ID
     * @param datacenterId 序列号
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATACENTER_ID));
        }
        WORK_ID = workerId;
        DATACENTER_ID = datacenterId;
    }

    /**
     * 获取下一个ID
     */
    public static synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < LAST_TIMESTAMP) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", LAST_TIMESTAMP - timestamp));
        }

        if (LAST_TIMESTAMP == timestamp) {
            // 当前毫秒内，则+1
            SEQUENCE = (SEQUENCE + 1) & SEQUENCE_MASK;
            if (SEQUENCE == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(LAST_TIMESTAMP);
            }
        } else {
            SEQUENCE = 0L;
        }
        LAST_TIMESTAMP = timestamp;
        // ID偏移组合生成最终的ID，并返回ID
        long nextId = ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (DATACENTER_ID << DATACENTER_ID_SHIFT)
                | (WORK_ID << WORKER_ID_SHIFT) | SEQUENCE;

        return nextId;
    }

    private static long tilNextMillis(final long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private static long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取 maxWorkerId
     */
    protected static long getMaxWorkerId(long datacenterId, long maxWorkerId) {
        StringBuffer mpId = new StringBuffer();
        mpId.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            // GET jvmPid
            mpId.append(name.split("@")[0]);
        }
        // MAC + PID 的 hashcode 获取16个低位
        return (mpId.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
    }

    /**
     * 数据标识id部分
     */
    protected static long getDatacenterId(long maxDatacenterId) {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (maxDatacenterId + 1);
            }
        } catch (Exception e) {
            System.out.println(" getDatacenterId: " + e.getMessage());
        }
        return id;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            long nextId = IdWorker.nextId();
            System.out.println(nextId);
        }
    }

}