package com.wxmylife.sample.handle;

public class HandleTest {

  public static void main(String[] args) {
    ThreadLocal<String> threadLocal = new ThreadLocal<>();
    threadLocal.set("This is  data");
    System.out.println("threadLocal value is " + threadLocal.get());

    CustomLopper.prepare();
    CustomHandle handle = new CustomHandle() {
      @Override public void handleMessage(CustomMessage msg) {
        super.handleMessage(msg);
        String value = (String) msg.object;
        System.out.println("handle message value is " + value);
      }
    };

    CustomMessage customMessage = new CustomMessage();
    customMessage.object = "简易Handle";
    handle.sendMessage(customMessage);
    CustomLopper.loop();
  }
}
