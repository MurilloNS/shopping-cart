package com.ollirum.ms_products.events;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductEventPublisher {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-key.products}")
    private String routingKey;

    public ProductEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishProductCreated(ProductEvent productEvent) {
        productEvent.setEventType("PRODUCT_CREATED");
        rabbitTemplate.convertAndSend(exchange, routingKey, productEvent);
    }
}