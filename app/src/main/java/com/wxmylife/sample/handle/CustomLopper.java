package com.wxmylife.sample.handle;

public class CustomLopper {

  static final ThreadLocal<CustomLopper> sThreadLocal = new ThreadLocal<>();

  final CustomMessageQueue messageQueue;

  private CustomLopper() {
    messageQueue = new CustomMessageQueue();
  }

  /**
   * 为当前线程初始化一个looper副本对象
   */
  public static void prepare() {
    if (sThreadLocal.get() != null) {
      throw new RuntimeException("Only one CustomLooper may be created per thread");
    }
    sThreadLocal.set(new CustomLopper());
  }

  /**
   * 获取当前线程的looper副本对象
   *
   * @return
   */
  public static CustomLopper myLopper() {
    return sThreadLocal.get();
  }

  /**
   * 轮询消息
   */
  public static void loop() {
    //获取当前线程的looper对象
    CustomLopper lopper = myLopper();
    CustomMessageQueue queue = lopper.messageQueue;

    for (; ; ) {
      //轮询消息，没有消息就阻塞
      CustomMessage msg = queue.next();

      //没有消息则阻塞
      if (msg == null) continue;

      //发送消息
      msg.target.dispatchMessage(msg);
    }


    /*while (true){
      CustomMessage message=queue.next();
      if (message!=null){
        message.target.dispatchMessage(message);
      }
    }*/
  }
}

