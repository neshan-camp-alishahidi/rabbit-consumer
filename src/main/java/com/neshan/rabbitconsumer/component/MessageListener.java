package com.neshan.rabbitconsumer.component;

import com.neshan.rabbitconsumer.message.CustomMessage;
import com.rabbitmq.client.Channel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageListener {

    RabbitTemplate template;

    @RabbitListener(queues = "q.message")
    public void listen(CustomMessage message, Channel channel) {
        log.info("Received message {}", message);
        String emailMessage = (String) template.convertSendAndReceive("x.post-create", "send-email", message);
        String smsMessage = (String) template.convertSendAndReceive("x.post-create", "send-sms", message);
        System.out.println(emailMessage);
        System.out.println(smsMessage);
    }
}
