package com.newminds.pubsub.redis.consumer.one;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Service;

/**
 * Created by Sunand on 22/07/2020.
 **/
@SpringBootApplication
public class ConsumerOneAppStarter {

  public static void main(String[] args) {
    SpringApplication.run(ConsumerOneAppStarter.class, args);

    //Redis Message Listener Container is not stopping the app from exiting.
    Object object = new Object();
    synchronized (object) {
      try {
        object.wait();
      } catch (InterruptedException e) {
        object.notifyAll();
      }
    }
  }
}

@Configuration
class ConsumerOneTopicConfiguration {

  @Bean
  public ConsumerOneMessageListener messageListener() {
    return new ConsumerOneMessageListener();
  }

  @Bean
  public MessageListenerAdapter messageListenerAdapter() {
    return new MessageListenerAdapter(messageListener());
  }

  @Bean
  public ChannelTopic topic() {
    return new ChannelTopic("pubsub:sample-demo");
  }

  @Bean
  public RedisMessageListenerContainer messageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
    final RedisMessageListenerContainer messageListenerContainer = new RedisMessageListenerContainer();
    messageListenerContainer.setConnectionFactory(redisConnectionFactory);
    messageListenerContainer.addMessageListener(messageListenerAdapter(), topic());
    return messageListenerContainer;
  }
}

@Service
class ConsumerOneMessageListener implements MessageListener {

  @Override
  public void onMessage(Message message, byte[] bytes) { //Message and Topic in bytes
    System.out.println("======================");
    System.out.println("Received : " + new String(message.getBody()));
    System.out.println("======================");
    System.out.println();
  }
}