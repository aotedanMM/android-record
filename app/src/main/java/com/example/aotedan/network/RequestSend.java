package com.example.aotedan.network;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestSend extends RequestHandler {
    private RequestCallBack requestCallBack;

    @Override
    public void successCallBack(String s) {
        requestCallBack.success(s);
    }

    @Override
    public void errorCallBack(String s) {
        requestCallBack.error(s);
    }

    public RequestSend post() {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = builder.build();
        //获取Request
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = getmOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Handler handler = getHandler();
                Message message = handler.obtainMessage();
                message.what = 0x122;
                message.obj = "失败";
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Handler handler = getHandler();
                Message message = handler.obtainMessage();
                message.what = 0x123;
                message.obj = response.body().string();
                handler.sendMessage(message);
            }
        });
        return this;
    }

    public RequestSend get() {
        StringBuffer stringBuffer = null;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer();
                stringBuffer.append(entry.getKey());
                stringBuffer.append("=");
                stringBuffer.append(entry.getValue());
            } else {
                stringBuffer.append("&");
                stringBuffer.append(entry.getKey());
                stringBuffer.append("=");
                stringBuffer.append(entry.getValue());
            }
        }
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer();
        }
        //2.创建Request对象，设置一个url地址（百度地址）,设置请求方式。
        Request request = new Request.Builder().url(url + "?" + stringBuffer.toString()).method("GET", null).build();
        //3.创建一个call对象,参数就是Request请求对象
        Call call = getmOkHttpClient().newCall(request);
        //4.请求加入调度，重写回调方法
        call.enqueue(new Callback() {
            //请求失败执行的方法
            @Override
            public void onFailure(Call call, IOException e) {
                Handler handler = getHandler();
                Message message = handler.obtainMessage();
                message.what = 0x122;
                message.obj = "失败";
                handler.sendMessage(message);
            }

            //请求成功执行的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Handler handler = getHandler();
                Message message = handler.obtainMessage();
                message.what = 0x123;
                message.obj = data;
                handler.sendMessage(message);
            }
        });
        return this;
    }

    private String url;

    public RequestSend url(String url) {
        this.url = url;
        return this;
    }

    private Map<String, String> map;

    public RequestSend params(Map<String, String> map) {
        this.map = map;
        return this;
    }

    public void CallBack(RequestCallBack requestCallBack) {
        this.requestCallBack = requestCallBack;
    }
}
