package com.newminds.pubsub.kafka.consumer.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Created by Sunand on 22/07/2020.
 **/
@SpringBootApplication
public class ConsumerOneAppStarter {

  public static void main(String[] args) {
    SpringApplication.run(ConsumerOneAppStarter.class, args);
  }
}

@Service
class ConsumerOneMessageListener {

  @KafkaListener(topics = "pubsub", groupId = "grp-1")
  public void onMessage(@Payload String message) {

    System.out.println("======================");
    System.out.println("Received : " + message);
    System.out.println("======================");
    System.out.println();
  }

}

