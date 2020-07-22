package com.newminds.pubsub.rabbitmq.consumer.two;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static org.springframework.amqp.core.BindingBuilder.bind;

/**
 * Created by Sunand on 22/07/2020.
 **/
@SpringBootApplication
@EnableRabbit
public class ConsumerTwoAppStarter {

  public static void main(String[] args) {
    SpringApplication.run(ConsumerTwoAppStarter.class, args);
  }
}

@Configuration
class ConsumerTwoQueueConfig {

  @Bean
  public Exchange fanoutExchange() {
    return ExchangeBuilder.fanoutExchange("fanout.exchange").durable(true).build();
  }

  @Bean
  public Queue consumer2Queue() {
    return QueueBuilder
            .durable("consumer_TWO_Q")
            .build();
  }

  @Bean
  public Binding declareBinding() {
    return bind(consumer2Queue())
            .to(fanoutExchange())
            .with("fanout.routing.key") //Routing Key will be ignored for Fanout Exchanges
            .noargs();
  }
}

@Service
@RabbitListener(queues = "consumer_TWO_Q")
class ConsumerTwoMessageListener {

  @RabbitHandler
  public void onMessage(@Payload String message) {
    System.out.println("======================");
    System.out.println("Received : " + message);
    System.out.println("======================");
    System.out.println();
  }
}


