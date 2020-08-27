package com.vishnu.springbootjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vishnu.springbootjms.config.JmsConfiguration;
import com.vishnu.springbootjms.model.QueueMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

/**
 * Class description
 *
 * @author : vishnu.g
 * created on : 26/Aug/2020
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class MessageSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Value("${activemq.destination}")
    private String destination;

    //@Scheduled(fixedRate = 2000)
    private void sendMessage(){


        QueueMessage message = QueueMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello World!")

                .build();

        jmsTemplate.convertAndSend(destination, message);

    }

    /**
     *
     * @param message
     * @param destination
     */
    public void sendMessage(QueueMessage message, String destination){
        jmsTemplate.setMessageIdEnabled(true);
        jmsTemplate.setMessageTimestampEnabled(true);
        jmsTemplate.convertAndSend(destination, message);
    }

    //@Scheduled(fixedRate = 2000)
    private void sendAndReceiveMessage() throws JMSException {

        QueueMessage message = QueueMessage
                .builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();

        Message receivedMsg = jmsTemplate.sendAndReceive(JmsConfiguration.SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage = null;

                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type", "com.vishnu.springbootjms.model.QueueMessage");

                    log.info("Sender >> sending msg : {}", message );

                    return helloMessage;

                } catch (JsonProcessingException e) {
                    log.error("Error in creating JMS Message, {}", e.getMessage());
                    throw new JMSException("Error in creating JMS Message, " + e.getMessage());
                }
            }
        });

        log.info("Sender >> received msg : {}", receivedMsg.getBody(String.class));

    }
}
