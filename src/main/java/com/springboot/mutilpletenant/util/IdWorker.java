package com.springboot.mutilpletenant.util;

public class IdWorker {

    private static long WORKER_ID = 2L;
    private final static long TWEPOCH = 1288834974657L;
    private static long sequence = 0L;
    private final static long WORKER_ID_BITS = 4L;
    public final static long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private final static long SEQUENCE_BITS = 10L;

    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    public final static long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    private static long lastTimestamp = -1L;

    public IdWorker(final long workerId) {
        super();
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0",
                    MAX_WORKER_ID));
        }
        WORKER_ID = workerId;
    }

    public static synchronized long nextId() {
        long timestamp = timeGen();
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                System.out.println("###########" + SEQUENCE_MASK);
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        if (timestamp < lastTimestamp) {
            try {
                throw new Exception(
                        String.format(
                                "Clock moved backwards.  Refusing to generate id for %d milliseconds",
                                lastTimestamp - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        lastTimestamp = timestamp;
        long nextId = ((timestamp - TWEPOCH << TIMESTAMP_LEFT_SHIFT))
                | (WORKER_ID << WORKER_ID_SHIFT) | (sequence);
        System.out.println("timestamp:" + timestamp + ",timestampLeftShift:"
                + TIMESTAMP_LEFT_SHIFT + ",nextId:" + nextId + ",workerId:"
                + WORKER_ID + ",sequence:" + sequence);
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

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(IdWorker.nextId());
        }
    }

}