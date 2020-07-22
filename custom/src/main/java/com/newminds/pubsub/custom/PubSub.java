package com.newminds.pubsub.custom;

import java.util.stream.Stream;

/**
 * Created by Sunand on 22/07/2020.
 **/
public class PubSub {

  public static void main(String[] args) {
    //Create Topic and Register Subscribers

    final Broker broker = Broker.getInstance();
    broker.createTopic("pubsub");

    broker.register("pubsub", new ConsumerOneSubscriber());
    broker.register("pubsub", new ConsumerTwoSubscriber());

    Publisher publisher = new Publisher();
    Stream.iterate(0, i -> i + 1)
            .forEach(integer -> {
              try {
                Thread.sleep(1_000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              publisher.publish("pubsub", "Catch Me - " + integer);
            });

  }
}


class ConsumerOneSubscriber implements Subscriber<String> {

  @Override
  public void onMessage(String message) {
    System.out.println("======================");
    System.out.println("Consumer One : " + message);
    System.out.println("======================");
    System.out.println();
  }
}

class ConsumerTwoSubscriber implements Subscriber<String> {

  @Override
  public void onMessage(String message) {
    System.out.println("======================");
    System.out.println("Consumer Two : " + message);
    System.out.println("======================");
    System.out.println();
  }
}