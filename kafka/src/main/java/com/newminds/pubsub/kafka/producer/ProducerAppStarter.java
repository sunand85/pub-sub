package com.newminds.pubsub.kafka.producer;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sunand on 22/07/2020.
 **/
@SpringBootApplication
public class ProducerAppStarter {

  public static void main(String[] args) {
    SpringApplication.run(ProducerAppStarter.class, args);
  }
}

@Configuration
class KafkaTopicConfiguration {

  @Bean
  public NewTopic defaultTopic() {
    return TopicBuilder.name("pubsub")
            .partitions(1)
            .replicas(1)
            .config(TopicConfig.RETENTION_MS_CONFIG, "-1") //Forever
            .build();
  }

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    return new KafkaAdmin(configs);
  }
}

@Service
class Producer {

  private final KafkaTemplate kafkaTemplate;

  public Producer(KafkaTemplate kafkaTemplate) {
    this.kafkaTemplate = kafkaTemplate;
  }

  @EventListener(ApplicationReadyEvent.class)
  public void onStartup() throws InterruptedException {
    for(int i = 0; i < 100; i++) {
      kafkaTemplate.send("pubsub", "Catch Me - " + i);
      Thread.sleep(2_000);
    }
  }
}
