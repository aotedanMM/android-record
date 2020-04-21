package com.example.aotedan.App;
import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import com.example.aotedan.utils.ToastUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Cookie;

@SuppressLint("Registered")
public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    public static ToastUtils toast;
    public static ConcurrentHashMap<String, List<Cookie>> cookieStore = new ConcurrentHashMap<>();
    public static String url = "http://192.168.1.50:8080/v1/api";
    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        toast = new ToastUtils(getApplicationContext());
        Log.i("toast", String.valueOf(toast));
    }
}


