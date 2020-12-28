package com.soundlab.mappers;

import com.soundlab.core.MessageDTO;

import java.util.Map;
import java.util.stream.Collectors;

import software.amazon.awssdk.services.sqs.model.Message;

public class MessageDTOMapper implements ModelMapper<Message, MessageDTO> {
    @Override
    public MessageDTO map(Message source) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(source.messageId());
        messageDTO.setBody(source.body());
        messageDTO.setAttributes(
            source
                .messageAttributes()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().stringValue()))
        );
        return messageDTO;
    }
}
