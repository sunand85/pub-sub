package com.newminds.pubsub.custom;

/**
 * Created by Sunand on 22/07/2020.
 **/
public interface Subscriber<P> {

  void onMessage(P p);
}
