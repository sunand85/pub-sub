package com.newminds.pubsub.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sunand on 22/07/2020.
 **/
public class Broker {

  private static List<String> topics = new ArrayList<>();
  private static Map<String, List<Subscriber>> topicSubscriber = new HashMap<>();

  private static final Broker BROKER = new Broker();

  public static Broker getInstance() {
    if(BROKER != null) return BROKER;
    else return new Broker();
  }

  public void createTopic(String name) {
    topics.add(name);
  }

  public List<Subscriber> getSubscribersByTopic(String name) {
    return topicSubscriber.get(name);
  }

  public void removeTopic(String name) {
    topics.remove(name);
    topicSubscriber.remove(name);
  }

  public void register(String topic, Subscriber subscriber) {
    if(topics.contains(topic)) {
      List<Subscriber> subscribers = topicSubscriber.get(topic);
      if(subscribers == null) {
        subscribers = new ArrayList<>();
      }
      subscribers.add(subscriber);
      topicSubscriber.put(topic, subscribers);
    }
  }

  public void unregister(String topic, Subscriber subscriber) {
    if(topics.contains(topic)) {
      topicSubscriber.get(topic).remove(subscriber);
    }
  }
}
