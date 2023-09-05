package com.neshan.rabbitconsumer.controller;

import com.neshan.rabbitconsumer.config.MQConfig;
import com.neshan.rabbitconsumer.message.CustomMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/message")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageController {

    RabbitTemplate template;

    @PostMapping("/publish")
    public String publish(@RequestBody CustomMessage message){
        message.setId(UUID.randomUUID().toString());
        message.setDate(new Date());
        template.convertAndSend(MQConfig.EXCHANGE_NAME,
                MQConfig.MESSAGE_ROUTING_KEY_NAME, message);

        return "Message published";
    }
}
