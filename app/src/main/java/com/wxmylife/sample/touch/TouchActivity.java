package com.wxmylife.sample.touch;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.wxmylife.sample.R;

public class TouchActivity extends AppCompatActivity {

  final String ACTION_TAG = "Activity";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_touch);
    findViewById(R.id.touchGroup).setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        Log.e(ACTION_TAG, "viewGroup----OnTouchListener---- 事件传递");
        return false;
      }
    });
    findViewById(R.id.touchGroup).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        Log.e(ACTION_TAG, "viewGroup----OnClickListener---- 事件传递");
      }
    });
  }

  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      Log.e(ACTION_TAG, "Activity----dispatchTouchEvent---- 事件传递");
    }

    // boolean dispatchTouchEvent=super.dispatchTouchEvent(ev);
    // Log.e(ACTION_TAG, "Activity----dispatchTouchEvent---- "+dispatchTouchEvent);

    return super.dispatchTouchEvent(ev);
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_UP) {
      Log.e(ACTION_TAG, "Activity----onTouchEvent---- 事件处理");
    }
    boolean touchEvent = super.onTouchEvent(event);
    Log.e(ACTION_TAG, "Activity----onTouchEvent---- " + touchEvent);
    return touchEvent;
  }
}
