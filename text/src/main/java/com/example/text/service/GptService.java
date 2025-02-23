package com.example.text.service;

import com.alibaba.fastjson2.JSONObject;
import com.example.text.pojo.dto.GptSendDto;
import com.example.text.pojo.http.response.DeepSeedResponse;

import java.util.Map;

public interface GptService {

    DeepSeedResponse send(GptSendDto dto);
}
