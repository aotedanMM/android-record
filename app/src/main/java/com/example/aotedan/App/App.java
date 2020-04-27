package com.example.aotedan.App;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.aotedan.utils.SharedHelper;
import com.example.aotedan.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Handler;

import okhttp3.Cookie;
import okhttp3.OkHttpClient;

//@SuppressLint("Registered")
public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    public static ToastUtils toast;
    public static ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();
    public static String url = "http://192.168.1.50:8080/v1/api";
    public static String token = "";
    public Context mContext;
    private SharedHelper sh;
    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        toast = new ToastUtils(getApplicationContext());
        // 读取本地token
        mContext = getApplicationContext();
        sh = new SharedHelper(mContext);
        Map<String,String> data = sh.read();
        token = data.get("token");
    }
}


