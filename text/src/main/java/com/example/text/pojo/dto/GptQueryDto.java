package com.example.text.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GptQueryDto {

    private String chatId;

    private String firstChatId;
}
