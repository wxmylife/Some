package com.wxmylife.sample.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

public class TouchView extends View {

  String ACTION_TAG = "View";

  public TouchView(Context context) {
    super(context);
  }

  public TouchView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public TouchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      Log.e(ACTION_TAG, "View----dispatchTouchEvent---- 事件传递");
    }
    boolean dispatchTouchEvent = super.dispatchTouchEvent(ev);
    Log.e(ACTION_TAG, "View----dispatchTouchEvent---- " + dispatchTouchEvent);

    return dispatchTouchEvent;
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_MOVE:
        Log.e(ACTION_TAG, "View----onTouchEvent---- ACTION_MOVE");
        break;
      case MotionEvent.ACTION_DOWN:
        Log.e(ACTION_TAG, "View----onTouchEvent---- ACTION_DOWN");
        break;
      default:
        break;
    }
    boolean touchEvent = super.onTouchEvent(event);
    Log.e(ACTION_TAG, "View----onTouchEvent---- " + touchEvent);

    return touchEvent;

  }
}
