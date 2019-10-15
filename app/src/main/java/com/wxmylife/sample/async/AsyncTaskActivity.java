package com.wxmylife.sample.async;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import com.wxmylife.sample.R;
import java.lang.ref.WeakReference;

public class AsyncTaskActivity extends AppCompatActivity {

  AppCompatTextView tvTip;
  AppCompatTextView tvValue;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_async);
    tvTip = findViewById(R.id.tvTip);
    tvValue = findViewById(R.id.tvValue);

    final NumberAsyncTask numberAsyncTask = new NumberAsyncTask(AsyncTaskActivity.this);
    findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        numberAsyncTask.execute(100);
      }
    });
    findViewById(R.id.btnStop).setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        numberAsyncTask.cancel(true);
      }
    });
  }

  private static class NumberAsyncTask extends AsyncTask<Integer, Integer, Void> {

    private WeakReference<AsyncTaskActivity> weakReference;

    public NumberAsyncTask(AsyncTaskActivity activity) {
      this.weakReference = new WeakReference<AsyncTaskActivity>(activity);
    }

    @Override protected void onPreExecute() {
      super.onPreExecute();
      AsyncTaskActivity asyncTaskActivity = weakReference.get();
      //这里是在异步操作之前执行，运行在UI线程
      if (asyncTaskActivity != null) {
        asyncTaskActivity.tvTip.setVisibility(View.VISIBLE);
      }
    }

    @Override protected Void doInBackground(Integer... integers) {
      int time = integers[0];
      for (int i = 0; i < time + 1; i++) {
        // 模拟耗时任务
        try {
          Thread.sleep(50);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        publishProgress(i);
      }
      return null;
    }

    @Override protected void onProgressUpdate(Integer... values) {
      super.onProgressUpdate(values);
      AsyncTaskActivity asyncTaskActivity = weakReference.get();
      asyncTaskActivity.tvValue.setText("进度---" + values[0].intValue());
    }

    @Override protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      AsyncTaskActivity asyncTaskActivity = weakReference.get();
      asyncTaskActivity.tvTip.setVisibility(View.GONE);
    }

    @Override protected void onCancelled() {
      super.onCancelled();
      AsyncTaskActivity asyncTaskActivity = weakReference.get();
      asyncTaskActivity.tvTip.setVisibility(View.GONE);
      asyncTaskActivity.tvValue.setText("倒计时");
    }
  }
}
