package com.ollirum.ms_orders.services.impl;

import com.ollirum.ms_orders.configuration.RabbitMQConfig;
import com.ollirum.ms_orders.events.ProductEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ProductEventListener {
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void handleProductEvent(ProductEvent event) {
        switch (event.getEventType()) {
            case "PRODUCT_CREATED" -> {
                System.out.println("Evento de produto recebido no ms-orders: " + event.getName());
            }
        }
    }
}