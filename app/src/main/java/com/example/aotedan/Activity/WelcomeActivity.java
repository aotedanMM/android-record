package com.example.aotedan.Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.aotedan.MainActivity;
import com.example.aotedan.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // 页面全屏
        startMainActivity();
    }
    private void startMainActivity(){
        TimerTask delayTask = new TimerTask() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(mainIntent);
                WelcomeActivity.this.finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(delayTask,3000);//延时5秒执行 run 里面的操作
    }
}
