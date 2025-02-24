package com.example.text.service;

import com.example.text.pojo.dto.GptSendDto;
import com.example.text.pojo.http.response.DeepSeedResponse;

public interface GptService {

    DeepSeedResponse send(GptSendDto dto);
}
