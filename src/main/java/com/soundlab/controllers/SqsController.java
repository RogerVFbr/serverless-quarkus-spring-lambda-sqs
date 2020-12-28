package com.soundlab.controllers;

import com.soundlab.core.MessageDTO;
import com.soundlab.core.SendMessageResponseDTO;
import com.soundlab.exceptions.NoMessagesInQueueException;
import com.soundlab.services.SqsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sqs")
public class SqsController {

    private SqsService sqsService;

    @Autowired
    public SqsController(SqsService sqsService) {
        this.sqsService = sqsService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<MessageDTO> getMessages() {
        List<MessageDTO> result = sqsService.getMessages();
        if(result.isEmpty()) throw new NoMessagesInQueueException();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST)
    public SendMessageResponseDTO sendMessage(@RequestBody Map<String, String> values) {
        return sqsService.sendMessage(values);
    }
}
