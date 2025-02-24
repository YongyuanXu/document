package com.example.text.pojo.input;

import com.example.text.pojo.dto.GptSendDto;
import com.example.text.pojo.http.response.DeepSeedResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

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
    private String totalTokens;
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

    public ChatLogInput(DeepSeedResponse response) {
        this.deleteFlag = 0;
        this.chatTimestamp = String.valueOf(System.currentTimeMillis());
        this.role = "system";
        this.content = getLastLine(response.getChoices().get(0).getMessage().getContent());
        this.totalTokens = response.getUsage().get("total_tokens").toString();
    }

    private String getLastLine(String str) {
        if (Strings.isBlank(str)) {
            return "";
        }
        String[] split = str.split("\n\n");
        if (split.length == 1) {
            // 没有换行符，返回整个字符串
            return str;
        }
        // 返回最后一个换行符后面的字符串
        return split[split.length - 1];
    }
}