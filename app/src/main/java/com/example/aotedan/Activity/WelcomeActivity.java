package com.example.aotedan.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.example.aotedan.App.App;
import com.example.aotedan.IndexActivity;
import com.example.aotedan.MainActivity;
import com.example.aotedan.R;
import com.example.aotedan.utils.SharedHelper;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {
    private SharedHelper sh;
    private Context mContext;
    private String  token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        mContext = getApplicationContext();
        sh = new SharedHelper(mContext);
        Map<String,String> data = sh.read();
        token = data.get("token");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); // 页面全屏
        startMainActivity();
    }
    private void startMainActivity(){
        TimerTask delayTask = new TimerTask() {
            @Override
            public void run() {
                // 没有token，则欢迎页后跳转到登录页
                if(token.equals("")) {
                    Intent mainIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(mainIntent);
                    WelcomeActivity.this.finish();
                } else {
                    // 有token，欢迎页后 跳转到首页
                    Intent mainIntent = new Intent(WelcomeActivity.this, IndexActivity.class);
                    startActivity(mainIntent);
                    WelcomeActivity.this.finish();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(delayTask,3000);//延时5秒执行 run 里面的操作
    }
}
