package com.springboot.mutilpletenant.util;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileReporter {

    public static String filePath = "/Users/zhangbo/Desktop/2021VAERSData/2021VAERSDATA.csv";

    public static void main(String[] args) {

        File file = new File(filePath);
        // 缓冲区大小 300M
        final int BUFFER_SIZE = 0x30000000;
        try {
            MappedByteBuffer inputBuffer = new RandomAccessFile(file, "r")
                    .getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            // 每次读出300M的内容
            byte[] dst = new byte[BUFFER_SIZE];
            long start = System.currentTimeMillis();
            for (int offset = 0; offset < inputBuffer.capacity(); offset += BUFFER_SIZE) {
                if (inputBuffer.capacity() - offset >= BUFFER_SIZE) {
                    for (int i = 0; i < BUFFER_SIZE; i++) {
                        dst[i] = inputBuffer.get(offset + i);
                    }
                } else {
                    for (int i = 0; i < inputBuffer.capacity() - offset; i++) {
                        dst[i] = inputBuffer.get(offset + i);
                    }
                }
                int length = (inputBuffer.capacity() % BUFFER_SIZE == 0) ? BUFFER_SIZE  : inputBuffer.capacity() % BUFFER_SIZE;
//                System.out.println(new String(dst, 0, length));
            }
            long end = System.currentTimeMillis();
            System.out.println("读取文件文件一半内容花费：" + (end - start) + "毫秒");
        } catch (IOException e) {
            e.printStackTrace();
        }



    }







}
