package com.newminds.pubsub.rabbitmq.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Created by Sunand on 22/07/2020.
 **/
@SpringBootApplication
public class ProducerAppStarter {

  public static void main(String args[]) {
    SpringApplication.run(ProducerAppStarter.class, args);
  }
}

@Service
class Producer {

  private final RabbitTemplate rabbitTemplate;

  public Producer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onStartup() {
    rabbitTemplate.setExchange("fanout.exchange");
    rabbitTemplate.convertAndSend("Catch Me");
  }
}

