package com.soundlab.mappers;

import com.soundlab.core.SendMessageResponseDTO;

import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

public class SendMessageResponseDTOMapper implements ModelMapper<SendMessageResponse, SendMessageResponseDTO> {
    @Override
    public SendMessageResponseDTO map(SendMessageResponse source) {
        SendMessageResponseDTO sendMessageResponseDTO = new SendMessageResponseDTO();
        sendMessageResponseDTO.setMessageId(source.messageId());
        return sendMessageResponseDTO;
    }
}
