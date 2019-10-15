package com.wxmylife.sample.handle;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class CustomMessageQueue {

  BlockingQueue<CustomMessage> queue = new ArrayBlockingQueue<>(50);

  public void enqueueMessage(CustomMessage message) {
    try {
      queue.put(message);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public CustomMessage next() {
    try {
      return queue.take();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }
}
