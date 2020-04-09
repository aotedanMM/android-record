package com.example.aotedan.network;


import android.os.Handler;
import android.os.Message;

import com.example.aotedan.App.App;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public abstract class RequestHandler {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x122:
                    errorCallBack(msg.obj.toString());
                    break;
                case 0x123:
                    successCallBack(msg.obj.toString());
                    break;
            }
        }
    };
    private OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {//这里可以做cookie传递，保存等操作
                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    App.cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {//加载新的cookies
                    List<Cookie> cookies = App.cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<Cookie>();
                }
            }).build();

    public abstract void successCallBack(String s);

    public abstract void errorCallBack(String s);

    public Handler getHandler() {
        return handler;
    }

    public OkHttpClient getmOkHttpClient() {
        return mOkHttpClient;
    }
}
