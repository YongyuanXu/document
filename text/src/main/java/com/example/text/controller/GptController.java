package com.example.text.controller;

import com.example.text.pojo.dto.GptSendDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gpt")
public class GptController {

    @PostMapping("/send")
    public String gpt(@RequestBody GptSendDto dto)  {
        return dto.toString();
    }
}
