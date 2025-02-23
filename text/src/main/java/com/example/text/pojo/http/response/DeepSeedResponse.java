package com.example.text.pojo.http.response;

import lombok.Data;

import java.util.LinkedHashMap;
import java.util.List;

@Data
public class DeepSeedResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private List<Choice> choices;
    private LinkedHashMap<String,Object> usage;
    private String systemFingerprint;

    @Data
    public static class Choice {
        private int index;
        private Message message;
        private String finishReason;

        @Data
        public static class Message {
            private String role;
            private String content;
        }
    }
}
