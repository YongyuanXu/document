package com.example.text.utils;

import java.util.UUID;

public class UUIDGenerator {
    /**
     * 生成一个32位的UUID，不包含横杠
     *
     * @return 32位无横杠的UUID字符串
     */
    public static String generateUUID() {
        // 生成一个标准的UUID
        UUID uuid = UUID.randomUUID();
        // 将UUID转换为字符串并去掉横杠
        return uuid.toString().replaceAll("-", "");
    }
}