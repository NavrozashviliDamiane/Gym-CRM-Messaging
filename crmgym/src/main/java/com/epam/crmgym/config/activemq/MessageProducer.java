package com.epam.crmgym.config.activemq;

import com.epam.crmgym.dto.client.TrainingSessionDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    public MessageProducer(JmsTemplate jmsTemplate, ObjectMapper objectMapper) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessageToTopicWithJwt(String jwtToken, TrainingSessionDTO sessionDTO) {
        try {
            // Serialize the sessionDTO object to JSON format
            String jsonSessionDTO = objectMapper.writeValueAsString(sessionDTO);

            // Set the JWT token as a message header
            jmsTemplate.setSessionTransacted(true);
            jmsTemplate.convertAndSend("Topic.example", jsonSessionDTO, message -> {
                message.setStringProperty("Authorization", "Bearer " + jwtToken);
                return message;
            });
        } catch (JsonProcessingException e) {
            // Handle serialization exception
            e.printStackTrace();
            // Or throw a custom exception, log, etc.
        }
    }
}
