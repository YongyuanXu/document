package com.example.text.pojo.input;

import com.example.text.pojo.dto.GptSendDto;
import com.example.text.utils.UUIDGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatLogInput {
    private String chatId;
    private String userName;
    private String lastChatId;
    private String firstChatId;
    private String role;
    private String content;
    private String model;
    private Integer promptTokens;
    private Integer completionTokens;
    private Integer totalTokens;
    private Integer cachedTokens;
    private Integer reasoningTokens;
    private Integer promptCacheHitTokens;
    private Integer promptCacheMissTokens;
    private String systemFingerprint;
    private String chatTimestamp;
    private Integer deleteFlag;

    public ChatLogInput(GptSendDto dto) {
        this.deleteFlag = 0;
        this.chatTimestamp = String.valueOf(System.currentTimeMillis());
        this.content = dto.getContent();
        this.userName = dto.getUserName();
        this.model = dto.getModel();
        this.role = "user";
    }
}