package com.example.text.mapper;


import com.example.text.pojo.dto.GptQueryDto;
import com.example.text.pojo.input.ChatLogInput;
import com.example.text.pojo.output.ChatLogOutput;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatLogMapper {
    List<ChatLogOutput> selectAll();

    List<ChatLogOutput> selectByIds(GptQueryDto dto);

    void insert(ChatLogInput chatLogInput);

    void deleteById(Long id);
}