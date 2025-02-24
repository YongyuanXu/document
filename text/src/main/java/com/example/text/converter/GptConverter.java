package com.example.text.converter;

import com.example.text.constant.YesOrNoEnum;
import com.example.text.exception.AssertUtils;
import com.example.text.exception.TextExceptionEnum;
import com.example.text.mapper.ChatLogMapper;
import com.example.text.pojo.dto.GptQueryDto;
import com.example.text.pojo.dto.GptSendDto;
import com.example.text.pojo.http.response.DeepSeedResponse;
import com.example.text.pojo.input.ChatLogInput;
import com.example.text.pojo.output.ChatLogOutput;
import com.example.text.utils.SpringContextUtil;
import com.example.text.utils.UUIDGenerator;

import java.util.List;

public class GptConverter {

    private static ChatLogMapper chatLogMapper;

    static {
        chatLogMapper = SpringContextUtil.getBean(ChatLogMapper.class);
    }

    public static ChatLogInput convert(GptSendDto dto) {
        ChatLogInput input = new ChatLogInput(dto);
        setChatId(input, dto);
        return input;
    }

    public static ChatLogInput convert(ChatLogInput latsInput, DeepSeedResponse response) {
        ChatLogInput input = new ChatLogInput(response);
        setChatId(input, latsInput);
        return input;
    }

    private static void setChatId(ChatLogInput thisInput, ChatLogInput lastInput)  {
        thisInput.setLastChatId(lastInput.getChatId());
        thisInput.setFirstChatId(lastInput.getFirstChatId());
        thisInput.setChatId(UUIDGenerator.generateUUID());
        thisInput.setUserName(lastInput.getUserName());
        thisInput.setModel(lastInput.getModel());
    }

    private static void setChatId(ChatLogInput input, GptSendDto dto) {
        input.setChatId(UUIDGenerator.generateUUID());
        if (dto.getIsFirstChat() == YesOrNoEnum.Y) {
            input.setFirstChatId(input.getChatId());
            return;
        }
        List<ChatLogOutput> outputs = chatLogMapper.selectByIds(new GptQueryDto(dto.getLastChatId(), null));
        AssertUtils.isOneItem(outputs, TextExceptionEnum.GPT_RECORDS_EXCEPTION.getMsg());
        input.setLastChatId(dto.getLastChatId());
        input.setFirstChatId(outputs.get(0).getFirstChatId());
    }
}
