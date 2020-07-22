package com.newminds.pubsub.rxjava;

import java.util.stream.IntStream;

/**
 * Created by Sunand on 22/07/2020.
 **/
public class RxPubSub {

  public static void main(String[] args) {
    final RxBus bus = RxBus.getInstance();
    new ConsumerOneMessageListener(bus);
    new ConsumerTwoMessageListener(bus);

    IntStream.range(0, 10).map(operand -> {
      bus.send("Catch Me - " + operand);
      try {
        Thread.sleep(1_000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      return operand;
    }).count();
  }
}

class ConsumerOneMessageListener {

  private RxBus bus;

  public ConsumerOneMessageListener(RxBus bus) {
    this.bus = bus;
    subscribe(bus);
  }

  private void subscribe(RxBus bus) {
    bus.toObservable().subscribe(message -> {
      System.out.println("Received By One : " + message);
      System.out.println();
    });
  }
}

class ConsumerTwoMessageListener {

  private RxBus bus;

  public ConsumerTwoMessageListener(RxBus bus) {
    this.bus = bus;
    subscribe(bus);
  }

  private void subscribe(RxBus bus) {
    bus.toObservable().subscribe(message -> {
      System.out.println("Received By Two : " + message);
      System.out.println();
    });
  }
}