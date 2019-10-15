package com.wxmylife.sample.handlethread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import com.wxmylife.sample.R;

public class HandleThreadActivity extends AppCompatActivity {

  private HandlerThread mHandlerThread;

  AppCompatTextView text;
  AppCompatButton button1,button2,button3;

  Handler mainHandler,workHandler;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_handlethread);
    mainHandler=new Handler();
    // 显示文本
    text =findViewById(R.id.text1);

    mHandlerThread=new HandlerThread("life");
    mHandlerThread.start();
    workHandler= new Handler(mHandlerThread.getLooper()){
      @Override public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
          //设置了两种消息处理操作,通过msg来进行识别
          switch(msg.what){
            // 消息1
            case 1:
              try {
                //延时操作
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              // 通过主线程Handler.post方法进行在主线程的UI更新操作
              mainHandler.post(new Runnable() {
                @Override
                public void run () {
                  Log.e("life","设置我爱学习");
                  text.setText("我爱学习");
                }
              });
              break;

            // 消息2
            case 2:
              try {
                Thread.sleep(3000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              mainHandler.post(new Runnable() {
                @Override
                public void run () {
                  Log.e("life","设置我不爱学习");
                  text.setText("我不喜欢学习");
                }
              });
              break;
            default:
              break;
          }
      }
    };
    button1 =  findViewById(R.id.button1);
    button1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        // 通过sendMessage（）发送
        // a. 定义要发送的消息
        Message msg = Message.obtain();
        msg.what = 1; //消息的标识
        msg.obj = "A"; // 消息的存放
        // b. 通过Handler发送消息到其绑定的消息队列
        workHandler.sendMessage(msg);
      }
    });

    // 点击Button2
    button2 = findViewById(R.id.button2);
    button2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        // 通过sendMessage（）发送
        // a. 定义要发送的消息
        Message msg = Message.obtain();
        msg.what = 2; //消息的标识
        msg.obj = "B"; // 消息的存放
        // b. 通过Handler发送消息到其绑定的消息队列
        workHandler.sendMessage(msg);
      }
    });

    // 点击Button3
    // 作用：退出消息循环
    button3 = findViewById(R.id.button3);
    button3.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mHandlerThread.quitSafely();
      }
    });

  }

  @Override protected void onDestroy() {
    super.onDestroy();
    mHandlerThread.quit();
  }
}
