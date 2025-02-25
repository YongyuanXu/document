package com.example.text.pojo.output;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChatLogOutput {
    private Long id;
    private String chatId;
    private String userName;
    private String lastChatId;
    private String firstChatId;
    private String role;
    private String content;
    private String model;
    private String totalTokens;
    private String systemFingerprint;
    private String chatTimestamp;
    private Integer deleteFlag;
}