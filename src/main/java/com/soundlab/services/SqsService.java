package com.soundlab.services;

import com.soundlab.core.MessageDTO;
import com.soundlab.core.SendMessageResponseDTO;
import com.soundlab.mappers.MessageDTOMapper;
import com.soundlab.mappers.SendMessageResponseDTOMapper;
import com.soundlab.utils.DurationMetric;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.MessageAttributeValue;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
public class SqsService {

    private SqsClient client;
    private String queueUrl;

    @Autowired
    public SqsService(SqsClient client,
                      @Value("${aws.sqs.url}") String queueUrl) {
        this.client = client;
        this.queueUrl = queueUrl;
    }

    public SendMessageResponseDTO sendMessage(Map<String, String> values) {
        DurationMetric duration = new DurationMetric();
        SendMessageResponse response = executeSdkSendMessage(buildMessageAttributeMap(values));
        duration.measure("AWS SQS message sent in");
        return new SendMessageResponseDTOMapper().map(response);
    }

    public List<MessageDTO> getMessages() {
        DurationMetric duration = new DurationMetric();
        List<Message> result = executeSdkReceiveMessages();
        duration.measure("AWS SQS messages received in");
        MessageDTOMapper mapper = new MessageDTOMapper();
        return result.stream().map(mapper::map).collect(Collectors.toList());
    }

    private List<Message> executeSdkReceiveMessages() {
        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageAttributeNames("*")
            .build();
        return client.receiveMessage(receiveMessageRequest).messages();
    }

    private SendMessageResponse executeSdkSendMessage(Map<String, MessageAttributeValue> values) {
        SendMessageRequest request = SendMessageRequest.builder()
            .queueUrl(queueUrl)
            .messageBody("Test message sent on: " + LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")))
            .messageAttributes(values)
            .build();
        return client.sendMessage(request);
    }

    private Map<String, MessageAttributeValue> buildMessageAttributeMap(Map<String, String> values) {
        return values
            .entrySet()
            .stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> MessageAttributeValue.builder()
                    .dataType("String")
                    .stringValue(e.getValue())
                    .build()
                )
            );
    }
}
