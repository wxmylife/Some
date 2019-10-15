package com.wxmylife.sample.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import androidx.annotation.Nullable;

public class GroupView extends LinearLayout {

  String ACTION_TAG = "ViewGroup";

  public GroupView(Context context) {
    super(context);
  }

  public GroupView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public GroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override public boolean dispatchTouchEvent(MotionEvent ev) {
    if (ev.getAction() == MotionEvent.ACTION_DOWN) {
      Log.e(ACTION_TAG, "ViewGroup----dispatchTouchEvent---- 事件传递");
    }

    // boolean dispatchTouchEvent=super.dispatchTouchEvent(ev);
    // Log.e(ACTION_TAG, "ViewGroup----dispatchTouchEvent---- "+dispatchTouchEvent);

    return super.dispatchTouchEvent(ev);
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    Log.e(ACTION_TAG, "ViewGroup----onInterceptTouchEvent---- 事件拦截");

    // boolean interceptTouchEvent=super.onInterceptTouchEvent(ev);
    // Log.e(ACTION_TAG, "ViewGroup----onInterceptTouchEvent---- "+interceptTouchEvent);

    return true;
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    switch (event.getAction()) {
      case MotionEvent.ACTION_MOVE:
        Log.e(ACTION_TAG, "ViewGroup----onTouchEvent---- ACTION_MOVE");
        break;
      case MotionEvent.ACTION_DOWN:
        Log.e(ACTION_TAG, "ViewGroup----onTouchEvent---- ACTION_DOWN");
        break;
      default:
        break;
    }
    boolean touchEvent = super.onTouchEvent(event);
    Log.e(ACTION_TAG, "ViewGroup----onTouchEvent---- " + touchEvent);

    return touchEvent;

  }

}

