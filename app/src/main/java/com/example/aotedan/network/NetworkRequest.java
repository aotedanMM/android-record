package com.example.aotedan.network;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.aotedan.App.App;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求
 *
 * @author Administrator
 */
public class NetworkRequest {
    // post请求 表单传参
    // mediaType 设置为 application/json
    public static void LoginPostFun(String url,JSONObject json,final  Request myRequest) {
        OkHttpClient okHttpClient = new OkHttpClient();
        MediaType mediaType  = MediaType.parse("application/json; charset=utf-8");
        Log.i("json",String.valueOf(json));
        RequestBody requestBody = FormBody.create(mediaType , json.toString());
        final okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i("error",String.valueOf(e));
                myRequest.error("失败");
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseData = response.body().string();//得到具体数据
                 Log.i("success",responseData);
                myRequest.success(responseData);
            }
        });
    }
    // post请求带参数 mediaType 为 application/json
    public static void RequestPostParams(Context context, String url, JSONObject json, final Request request) {
        if (!NetworkUtils.isNetConnected(context)) {
            App.toast.ToastMessageLong("当前无网络连接哦");
            request.error("未访问");

        } else {
            if (!NetworkUtils.isNetAvailable(context)) {
                App.toast.ToastMessageLong("网络开小差了哦");
                request.error("未访问");

            } else {
                OkHttpUtils
                        .postString()
                        .url(url)
                        .content(String.valueOf(json))
                        .mediaType(MediaType.parse("application/json; charset=utf-8"))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {
                                App.toast.ToastMessageLong("网络开小差了哦");
                                request.error("失败");
                            }
                            @Override
                            public void onResponse(String resp, int i) {
                                request.success(resp);
                            }
                        });
            }
        }
    }
    // get请求带参数
    public static void RequestGetParams(Context context,String url, int flag,int page,int limit ,final Request request) {
        if (!NetworkUtils.isNetConnected(context)) {
            App.toast.ToastMessageLong("当前无网络连接哦");
            request.error("未访问");

        } else {
            if (!NetworkUtils.isNetAvailable(context)) {
                App.toast.ToastMessageLong("网络开小差了哦");
                request.error("未访问");

            } else {
                OkHttpUtils
                        .get()
                        .url(url)
                        .addParams("gasFlag", String.valueOf(flag))
                        .addParams("page",String.valueOf(page))
                        .addParams("limit",String.valueOf(limit))
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {

                            }

                            @Override
                            public void onResponse(String s, int i) {

                            }
                        });

            }
        }
    }
    // get请求
    public static void RequestGet(Context context,String url,final Request request) {
        if (!NetworkUtils.isNetConnected(context)) {
            App.toast.ToastMessageLong("当前无网络连接哦");
            request.error("未访问");

        } else {
            if (!NetworkUtils.isNetAvailable(context)) {
                App.toast.ToastMessageLong("网络开小差了哦");
                request.error("未访问");

            } else {
                OkHttpUtils
                        .get()
                        .url(url)
                        .build()
                        .execute(new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int i) {
                            }

                            @Override
                            public void onResponse(String s, int i) {
                            }
                        });

            }
        }
    }
}
