package com.epam.trainerworkload.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageConsumer {

    private final ObjectMapper objectMapper;
    private final JmsTemplate jmsQueueTemplate;

    @Value("${jwt.token.header}")
    private String jwtTokenHeader;

    public MessageConsumer(ObjectMapper objectMapper, JmsTemplate jmsQueueTemplate) {
        this.objectMapper = objectMapper;
        this.jmsQueueTemplate = jmsQueueTemplate;
    }

    @JmsListener(destination = "Topic.example", containerFactory = "topicListenerFactory", subscription = "trainer-workload-durable-subscription")
    public void receiveTrainingSession(Message message) {
        try {
            if (message instanceof TextMessage) {
                String jsonSessionDTO = ((TextMessage) message).getText();
                log.info("Received training session: {}", jsonSessionDTO);

                jmsQueueTemplate.convertAndSend("Queue.intermediate", jsonSessionDTO, m -> {
                    String jwtToken = message.getStringProperty(jwtTokenHeader);
                    if (jwtToken != null) {
                        m.setStringProperty("Authorization", jwtToken);
                    }
                    return m;
                });

            } else {
                log.warn("Received message is not a TextMessage. Skipping processing.");
            }
        } catch (Exception e) {
            log.error("Error occurred while processing the received message: {}", e.getMessage());
        }
    }
}

