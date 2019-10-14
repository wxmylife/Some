package com.wxmylife.sample.handle;

public class CustomHandle {

  private CustomLopper mLopper;

  private CustomMessageQueue mQueue;

  public CustomHandle() {
    mLopper = CustomLopper.myLopper();
    if (mLopper == null) {
      throw new RuntimeException(
          "Can't create handler inside thread " + Thread.currentThread()
              + " that has not called Looper.prepare()");
    }
    mQueue = mLopper.messageQueue;
  }

  public void sendMessage(CustomMessage message) {
    enqueueMessage(message);
  }

  /**
   * 发送消息至队列
   *
   * @param message
   */
  public void enqueueMessage(CustomMessage message) {
    message.target = this;
    mQueue.enqueueMessage(message);
  }

  /**
   * 分发消息
   *
   * @param message
   */
  public void dispatchMessage(CustomMessage message) {
    handleMessage(message);
  }

  /**
   * 处理消息
   *
   * @param msg
   */
  public void handleMessage(CustomMessage msg) {
  }

}
