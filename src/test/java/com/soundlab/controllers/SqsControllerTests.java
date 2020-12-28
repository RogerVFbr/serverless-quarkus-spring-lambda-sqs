package com.soundlab.controllers;

import com.soundlab.core.MessageDTO;
import com.soundlab.core.SendMessageResponseDTO;
import com.soundlab.exceptions.NoMessagesInQueueException;
import com.soundlab.services.SqsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SqsControllerTests {

    @Mock
    private SqsService sqsService;

    private SqsController sqsController;

    @BeforeEach
    void initialize() {
        MockitoAnnotations.initMocks(this);
        sqsController = new SqsController(sqsService);
    }

    @Test
    void whenRequestMessages_andMessagesExist_retrieveMessages() {
        String messageId = "mockMessageId";
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setMessageId(messageId);
        List<MessageDTO> messages = Collections.singletonList(messageDTO);

        Mockito.doReturn(messages)
            .when(sqsService)
            .getMessages();

        List<MessageDTO> result = sqsController.getMessages();

        assertEquals(Objects.requireNonNull(result.get(0).getMessageId()), messageId);
    }

    @Test
    void whenRequestMessages_andNoMessagesExist_throwsError() {
        Mockito.doReturn(Collections.EMPTY_LIST)
            .when(sqsService)
            .getMessages();

        assertThrows(NoMessagesInQueueException.class, () -> sqsController.getMessages());
    }

    @Test
    void whenSendMessages_sendingSucceeds() {
        String messageId = "mockMessageId";
        SendMessageResponseDTO sendMessageResponseDTO = new SendMessageResponseDTO();
        sendMessageResponseDTO.setMessageId(messageId);

        Mockito.doReturn(sendMessageResponseDTO)
            .when(sqsService)
            .sendMessage(Mockito.anyMap());

        SendMessageResponseDTO result = sqsController.sendMessage(new HashMap<>());

        assertEquals(Objects.requireNonNull(result.getMessageId()), messageId);
    }
}
