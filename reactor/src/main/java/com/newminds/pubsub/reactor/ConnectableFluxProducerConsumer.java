package com.newminds.pubsub.reactor;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sunand on 22/07/2020.
 **/
public class ConnectableFluxProducerConsumer {
  public static void main(String[] args) throws InterruptedException {

    final ConnectableFlux<Integer> publish = Flux.just(1, 2, 3, 4, 5).publish();

    new Thread(() -> {
      publish.autoConnect().subscribe(integer -> System.out.println("One - " + integer));
    }).start();

    new Thread(() -> {
      System.out.println("I am in");
      publish.autoConnect().subscribe(integer -> System.out.println("Two - " + integer));
    }).start();

    new CountDownLatch(1).await(100, TimeUnit.SECONDS);
  }
}
