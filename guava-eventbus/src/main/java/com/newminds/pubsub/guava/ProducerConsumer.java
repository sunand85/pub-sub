package com.newminds.pubsub.guava;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import java.util.stream.Stream;

/**
 * Created by Sunand on 22/07/2020.
 **/
public class ProducerConsumer {

  public static void main(String[] args) throws InterruptedException {
    System.out.println("I am here");

    EventBus eventBus = new EventBus();
    eventBus.register(new ConsumerOneListener());
    eventBus.register(new ConsumerTwoListener());

    //Producer Code
    //Infinite Stream
    Stream.iterate(0, integer -> integer + 1).map(operand -> {
      eventBus.post("Catch Me - " + operand);
      try {
        Thread.sleep(1_000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return operand;
    }).count(); //Count is required to start the stream

    /*Object lock = new Object();
    synchronized (lock) {
      lock.wait();
    }*/
  }
}

interface MessageListener<P> {
  void onMessage(P p);
}

class ConsumerOneListener implements MessageListener<String> {

  @Override
  @Subscribe
  public void onMessage(String message) {
    System.out.println("======================");
    System.out.println("Consumer One : " + message);
    System.out.println("======================");
    System.out.println();
  }
}

class ConsumerTwoListener implements MessageListener<String> {

  @Override
  @Subscribe
  public void onMessage(String message) {
    System.out.println("======================");
    System.out.println("Consumer Two : " + message);
    System.out.println("======================");
    System.out.println();
  }
}