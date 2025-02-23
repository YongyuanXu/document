package com.example.text.controller;

import com.example.text.pojo.dto.GptSendDto;
import com.example.text.pojo.vo.GptSendVo;
import com.example.text.service.GptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gpt")
public class GptController {

    @Autowired
    private GptService gptService;

    @PostMapping("/send")
    public GptSendVo gpt(@RequestBody GptSendDto dto)  {
        gptService.send(dto);
        return null;
    }
}
