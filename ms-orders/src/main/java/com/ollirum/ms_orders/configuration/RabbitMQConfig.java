package com.ollirum.ms_orders.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "product.created.queue";
    public static final String EXCHANGE_NAME = "product.exchange";
    public static final String ROUTING_KEY = "product.created";

    @Bean
    public Queue productQueue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public DirectExchange productExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding productBinding() {
        return BindingBuilder
                .bind(productQueue())
                .to(productExchange())
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}