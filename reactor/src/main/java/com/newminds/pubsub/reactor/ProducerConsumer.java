package com.newminds.pubsub.reactor;

import reactor.core.publisher.Flux;

import java.util.stream.Stream;

/**
 * Created by Sunand on 22/07/2020.
 **/
public class ProducerConsumer {
  public static void main(String[] args) {

    final Flux<Integer> flux = producer();

    //To simulate app running in its own Thread Context
    Runnable consumerOne = () -> consumerOne(flux);
    new Thread(consumerOne).start();

    Runnable consumerTwo = () -> consumerTwo(flux);
    new Thread(consumerTwo).start();
  }

  private static Flux<Integer> producer() {
    return Flux
            .fromStream(
                    Stream
                            .iterate(0, i -> i + 1) //Infinite Stream
                            .map(integer -> {
                              try {
                                Thread.sleep(1_000); //Adding Delay
                              } catch (InterruptedException e) {
                                e.printStackTrace();
                              }
                              return integer;
                            })
            ).share();
  }

  private static void consumerOne(Flux<Integer> flux) {
    flux.subscribe(integer -> System.out.println("Received By One : " + integer));
  }

  private static void consumerTwo(Flux<Integer> flux) {
    flux.subscribe(integer -> System.out.println("Received By Two : " + integer));
  }
}
