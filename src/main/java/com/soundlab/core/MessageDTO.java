package com.soundlab.core;

import java.util.Map;

import lombok.Data;

@Data
public class MessageDTO {
    private String messageId;
    private String body;
    private Map<String, String> attributes;
}
