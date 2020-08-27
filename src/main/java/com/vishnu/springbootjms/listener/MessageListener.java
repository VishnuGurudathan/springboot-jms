package com.vishnu.springbootjms.listener;

import com.vishnu.springbootjms.config.JmsConfiguration;
import com.vishnu.springbootjms.controllers.MessageController;
import com.vishnu.springbootjms.model.QueueMessage;
import com.vishnu.springbootjms.model.TempStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Listener class for receiving JMS message.
 * Normally listener will be part of another application.
 *
 * @author : vishnu.g
 * created on : 26/Aug/2020
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class MessageListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = "${activemq.destination}")
    private void listen(@Payload QueueMessage queueMessage,
                       @Headers MessageHeaders headers, Message message){

        log.info("received msg : {}", queueMessage);
        // uncomment and view to see retry count in debugger
        // throw new RuntimeException("foo");

    }

    @JmsListener(destination = JmsConfiguration.SEND_RCV_QUEUE)
    private void listenForHello(@Payload QueueMessage queueMessage,
                               @Headers MessageHeaders headers, Message jmsMessage,
                               org.springframework.messaging.Message springMessage) throws JMSException, JMSException {

        QueueMessage payloadMsg = QueueMessage
                .builder()
                .id(UUID.randomUUID())
                .message("World!!")
                .build();

        //example to use Spring Message type
        // jmsTemplate.convertAndSend((Destination) springMessage.getHeaders().get("jms_replyTo"), "got it!");

        log.info("Listener >> received msg : {}", queueMessage);
        log.info("Listener >> replaying to : {} with payload : {}", jmsMessage.getJMSReplyTo(), payloadMsg);
        jmsTemplate.convertAndSend(jmsMessage.getJMSReplyTo(), payloadMsg);

    }

    @JmsListener(destination = MessageController.MESSAGE_QUEUE)
    public void receiveMessage(@Payload QueueMessage queueMessage,
                        @Headers MessageHeaders headers, Message message){

        log.info("received msg : {}", queueMessage);
        TempStore.tempStore.add(queueMessage);

    }
}
