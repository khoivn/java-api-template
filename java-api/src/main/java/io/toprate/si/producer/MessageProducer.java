package io.toprate.si.producer;

import io.toprate.si.statics.SiExchange;
import io.toprate.si.statics.SiRoutingKey;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MessageProducer {

    private static final Logger log = LoggerFactory.getLogger(MessageProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(Object message, SiExchange exchange, SiRoutingKey routingKey) {
        log.info("Sending message...");
        rabbitTemplate.convertAndSend(exchange.getName(), routingKey.getName(), message);
    }
}