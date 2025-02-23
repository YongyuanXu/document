package com.example.text.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.example.text.constant.YesOrNoEnum;
import com.example.text.exception.AssertUtils;
import com.example.text.exception.TextException;
import com.example.text.exception.TextExceptionEnum;
import com.example.text.mapper.ChatLogMapper;
import com.example.text.pojo.dto.GptQueryDto;
import com.example.text.pojo.dto.GptSendDto;
import com.example.text.pojo.http.response.DeepSeedResponse;
import com.example.text.pojo.input.ChatLogInput;
import com.example.text.pojo.output.ChatLogOutput;
import com.example.text.service.GptService;
import com.example.text.service.common.HttpService;
import com.example.text.utils.CollectionUtils;
import com.example.text.utils.UUIDGenerator;
import org.mapstruct.ap.internal.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GptServiceImpl implements GptService {

    @Value("${gpt.url}")
    private String url;

    @Value("${gpt.auth}")
    private String auth;

    @Autowired
    private HttpService httpService;
    @Autowired
    private ChatLogMapper chatLogMapper;

    @Override
    public DeepSeedResponse send(GptSendDto dto) {
        checkParams(dto);
        JSONObject rawResp = httpService.sendRequest(url, HttpMethod.POST, createGptHeaders(), createGptRequestBody(dto));
        DeepSeedResponse response = JSONObject.parseObject(rawResp.toJSONString(), DeepSeedResponse.class);
        saveMsg(dto, response);
        return response;
    }

    private void checkParams(GptSendDto dto) {
        if (dto.getIsFirstChat() == YesOrNoEnum.Y) {
            return;
        }
        AssertUtils.notNull(dto.getLastChatId(), TextExceptionEnum.GPT_PARAMS_EXCEPTION.getMsg());
    }

    private void saveMsg(GptSendDto dto, DeepSeedResponse response) {
        ChatLogInput input = new ChatLogInput(dto);
        setChatId(input, dto);
        String content = response.getChoices().get(0).getMessage().getContent();
        chatLogMapper.insert(input);
    }

    private void setChatId(ChatLogInput input, GptSendDto dto) {
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

    private JSONObject createGptRequestBody(GptSendDto dto) {
        if (dto.getIsFirstChat() == YesOrNoEnum.Y) {
            return firstChatBody(dto);
        }
        List<ChatLogOutput> chatLogOutputs = chatLogMapper.selectByIds(new GptQueryDto(dto.getLastChatId(), null));
        AssertUtils.isNotEmpty(chatLogOutputs, TextExceptionEnum.GPT_RECORDS_EXCEPTION.getMsg());
        chatLogOutputs.sort(Comparator.comparing(ChatLogOutput::getChatTimestamp));
        JSONObject body = initChatBody(dto);
        List<Map<String, String>> messages = new ArrayList<>();
        chatLogOutputs.forEach(output -> messages.add(fillChatMsg(output)));
        messages.add(initUserMsg(dto.getContent()));
        body.put("messages", messages);
        return body;
    }

    private Map<String, String> fillChatMsg(ChatLogOutput output) {
        Map<String, String> msg = new HashMap<>();
        msg.put("role", output.getRole());
        msg.put("content", output.getContent());
        return msg;
    }

    private JSONObject firstChatBody(GptSendDto dto) {
        JSONObject body = initChatBody(dto);
        body.put("messages", Arrays.asList(initChatMsg(), initUserMsg(dto.getContent())));
        return body;
    }

    private JSONObject initChatBody(GptSendDto dto) {
        JSONObject body = new JSONObject();
        body.put("model", dto.getModel());
        body.put("stream", false);
        return body;
    }

    private Map<String, String> createGptHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + auth);
        return headers;
    }

    private Map<String, String> initChatMsg() {
        Map<String, String> msg = new HashMap<>();
        msg.put("role", "system");
        msg.put("content", "You are a helpful assistant.");
        return msg;
    }

    private Map<String, String> initUserMsg(String content) {
        Map<String, String> msg = new HashMap<>();
        msg.put("role", "user");
        msg.put("content", content);
        return msg;
    }
}
