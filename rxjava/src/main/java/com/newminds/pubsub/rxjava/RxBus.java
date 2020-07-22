package com.newminds.pubsub.rxjava;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * Created by Sunand on 22/07/2020.
 **/
public class RxBus {

  private PublishSubject<Object> bus = PublishSubject.create();

  private static RxBus RXBUS = new RxBus();

  public static RxBus getInstance() {
    if(RXBUS != null) return RXBUS;
    else return new RxBus();
  }

  private RxBus() {
  }

  public void send(Object o) {
    bus.onNext(o);
  }

  public Observable<Object> toObservable() {
    return bus;
  }
}
