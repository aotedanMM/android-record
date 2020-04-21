package com.example.aotedan.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.example.aotedan.App.App;
import com.example.aotedan.utils.FDialogUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 网络请求
 *
 * @author Administrator
 */
public class NetworkRequest {
    static ProgressDialog progressDialogBA;
    // post数据请求
    public static void requestPost(Context context,String url,
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
                                Log.i("resp",arg0);
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

    static FDialogUtils fDialogUtils;

    // 开启进度弹框
    public static void press(final Context context) {

        fDialogUtils = new FDialogUtils(context);
    }

    // 关闭进度弹框
    public static void dimssPress() {
        if (fDialogUtils != null) {
            fDialogUtils.dimss();
        }
    }
}
