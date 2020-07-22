package com.newminds.pubsub.custom;

import java.util.List;

/**
 * Created by Sunand on 22/07/2020.
 **/
public class Publisher {

  public static Broker BROKER = Broker.getInstance();

  void publish(final String topic, final Object payload) {
    final List<Subscriber> subscribersByTopic = BROKER.getSubscribersByTopic(topic);
    subscribersByTopic.parallelStream().forEach(subscriber -> {
      subscriber.onMessage(payload);
    });
  }
}
