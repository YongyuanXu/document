package com.example.text.pojo.dto;

import com.example.text.constant.YesOrNoEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;

@Data
@ToString
public class GptSendDto {

    @NotBlank(message = "model cannot be blank")
    private String model;

    @NotBlank(message = "content cannot be blank")
    private String content;

    @NonNull
    private YesOrNoEnum isFirstChat;

    @NotBlank(message = "userName cannot be blank")
    private String userName;

    private Integer lastChatId;

    public void init() {
        if(this.lastChatId == null) {
            this.isFirstChat = YesOrNoEnum.Y;
            return;
        }
        this.isFirstChat = YesOrNoEnum.N;
    }
}
