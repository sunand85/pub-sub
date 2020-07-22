package com.newminds.pubsub.redis.prodcuer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by Sunand on 22/07/2020.
 **/
@SpringBootApplication
public class ProducerAppStarter {

  public static void main(String[] args) {
    SpringApplication.run(ProducerAppStarter.class, args);
  }
}

@Service
class Producer {
  private final StringRedisTemplate redisTemplate;

  public Producer(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onStartup() throws InterruptedException {
    for(int i = 0; i < 100; i++) {
      redisTemplate.convertAndSend("pubsub:sample-demo", "Catch Me - " + i);
      Thread.sleep(1_000);
    }
  }
}