package com.vishnu.springbootjms.controllers;

import com.vishnu.springbootjms.model.QueueMessage;
import com.vishnu.springbootjms.model.TempStore;
import com.vishnu.springbootjms.sender.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Rest controller to send message to queue.
 *
 * @author : vishnu.g
 * created on : 27/Aug/2020
 */
@RestController
@RequestMapping(value = "/api/messages")
@Slf4j
public class MessageController {

    public static final String MESSAGE_QUEUE = "message-queue";
    private final MessageSender messageSender;

    public MessageController(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @PostMapping
    public String sendMessage(@RequestBody QueueMessage message) {
        message.setId(UUID.randomUUID());
        log.info("incoming msg : {}",message);
        messageSender.sendMessage(message,MESSAGE_QUEUE);
        return "success";
    }

    @GetMapping
    public List<QueueMessage> message() {
        log.debug("stored data : {}", TempStore.tempStore);
        return TempStore.tempStore;
    }
}
