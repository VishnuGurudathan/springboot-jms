package com.vishnu.springbootjms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

/**
 * Configuration class for JMS
 *
 * @author : vishnu.g
 * created on : 26/Aug/2020
 */
@Configuration
public class JmsConfiguration {


    public static final String SEND_RCV_QUEUE = "reply-back-queue";
    /**
     * Serialize message content to json
     * MessageConverter: We are using MappingJackson2MessageConverter, and the target type is of Text format.
     * The DTO object sent to the message queue in the JSON text format.
     * @return {@link MessageConverter}
     */
    @Bean
    public MessageConverter messageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        /*
        * TypeId: An id that should match both on the consumer and producer side.
        * We can set any value as TypeID.
        * */
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
