package com.newminds.pubsub.rabbitmq.consumer.one;

import org.springframework.amqp.core.*;
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
public class ConsumerOneAppStarter {

  public static void main(String[] args) {
    SpringApplication.run(ConsumerOneAppStarter.class, args);
  }
}

@Configuration
class ConsumerOneQueueConfig {

  @Bean
  public Exchange fanoutExchange() {
    return ExchangeBuilder.fanoutExchange("fanout.exchange").durable(true).build();
  }

  @Bean
  public Queue consumer1Queue() {
    return QueueBuilder
            .durable("consumer_ONE_Q")
            .build();
  }

  @Bean
  public Binding declareBinding() {
    return bind(consumer1Queue())
            .to(fanoutExchange())
            .with("fanout.routing.key") //Routing Key will be ignored for Fanout Exchanges
            .noargs();
  }
}

@Service
@RabbitListener(queues = "consumer_ONE_Q")
class ConsumerOneMessageListener {

  @RabbitHandler
  public void onMessage(@Payload String message) {
    System.out.println("======================");
    System.out.println("Received : " + message);
    System.out.println("======================");
    System.out.println();
  }
}


