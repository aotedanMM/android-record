package com.example.aotedan.aotedanHttp;

import android.content.Context;
import android.util.Log;

import com.example.aotedan.App.App;
import com.example.aotedan.network.NetworkUtils;
import com.example.aotedan.network.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkHttp {
    public static void LoginPost(Context context, String url,
                                   Map<String, String> map, final Request request) {
        if (!NetworkUtils.isNetConnected(context)) {
            App.toast.ToastMessageLong("当前无网络连接哦");
            request.error("未访问");

        } else {
            if (!NetworkUtils.isNetAvailable(context)) {
                App.toast.ToastMessageLong("网络开小差了哦");
                request.error("未访问");

            } else {
                OkHttpUtils.post().url(url).params(map).build()
                        .execute(new StringCallback() {

                            @Override
                            public void onResponse(String arg0, int arg1) {

                                request.success(arg0);

                            }

                            @Override
                            public void onError(Call arg0, Exception arg1,
                                                int arg2) {
                                Log.e("arg0", arg1 + "===" + arg2);

                                App.toast.ToastMessageLong("网络开小差了哦");
                                request.error("失败");

                            }
                        });
            }
        }
    }

}
