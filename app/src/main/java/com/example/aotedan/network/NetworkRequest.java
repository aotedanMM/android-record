package com.example.aotedan.network;

import android.content.Context;

import com.example.aotedan.App.App;

import java.util.Map;

/**
 * 网络请求
 *
 * @author Administrator
 */
public class NetworkRequest {

    public static void postTo(String url, Map<String, String> map, final RequestCallBack requestCallBack) {

        RequestSend requestSend = new RequestSend();
        requestSend.url(url).params(map).post().CallBack(new RequestCallBack() {
            @Override
            public void success(String str) {
                requestCallBack.success(str);
            }

            @Override
            public void error(String error) {
                requestCallBack.error(error);
            }
        });
    }

    public static void postToNetConnected(Context context, String url, Map<String, String> map, final RequestCallBack requestCallBack) {
        if (!NetworkUtils.isNetConnected(context)) {
            App.toast.ToastMessageLong("当前无网络连接哦");
            requestCallBack.error("未访问");
        } else {
            if (!NetworkUtils.isNetAvailable(context)) {
                App.toast.ToastMessageLong("网络开小差了哦");
                requestCallBack.error("未访问");
            } else {
                postTo(url, map, requestCallBack);
            }
        }
    }


    public static void getTo(String url, Map<String, String> map, final RequestCallBack requestCallBack) {
        RequestSend requestSend = new RequestSend();
        requestSend.url(url).params(map).get().CallBack(new RequestCallBack() {
            @Override
            public void success(String str) {
                requestCallBack.success(str);
            }

            @Override
            public void error(String error) {
                requestCallBack.error(error);
            }
        });
    }

    public static void getToNetConnected(Context context, String url, Map<String, String> map, final RequestCallBack requestCallBack) {
        if (!NetworkUtils.isNetConnected(context)) {
            App.toast.ToastMessageLong("当前无网络连接哦");
            requestCallBack.error("未访问");
        } else {
            if (!NetworkUtils.isNetAvailable(context)) {
                App.toast.ToastMessageLong("网络开小差了哦");
                requestCallBack.error("未访问");
            } else {
                getTo(url, map, requestCallBack);
            }
        }
    }

}