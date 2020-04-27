package com.example.aotedan.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aotedan.App.App;
import com.example.aotedan.R;
import com.example.aotedan.bean.DownHoleNumBean;
import com.example.aotedan.bean.WarnAreaNumBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends Fragment{
    private Handler handler;
    private DownHoleNumBean downHoleNum;
    private WarnAreaNumBean warnAreaNum;
    private JSONObject personNumData;
    private String sumStaff;
    private String overTimeStaff;
    private String seriousTimeStaff;
    private String unAttendanceStaff;
    private String importantStaff;
    private String limitStaff;
    private View home;
    private TextView person_sort1_num;
    private TextView person_sort2_num;
    private TextView person_sort3_num;
    private TextView person_sort4_num;
    private TextView person_sort5_num;
    private TextView person_sort6_num;
    private String api_url;
    private int staffNumType;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        home = inflater.inflate(R.layout.fragment_home, container, false);
        //创建属于主线程的handler
        handler=new Handler();
        initView();
        return home;
    }
    // 初始化控件
    private void initView() {
        person_sort1_num = (TextView) home.findViewById(R.id.person_sort1_num);
        person_sort2_num = (TextView) home.findViewById(R.id.person_sort2_num);
        person_sort3_num = (TextView) home.findViewById(R.id.person_sort3_num);
        person_sort4_num = (TextView) home.findViewById(R.id.person_sort4_num);
        person_sort5_num = (TextView) home.findViewById(R.id.person_sort5_num);
        person_sort6_num = (TextView) home.findViewById(R.id.person_sort6_num);
        handleFetchAllStaffNum();
    }
    // 获取井下各类人数
    private void handleFetchAllStaffNum() {
        handleFetchTotalNum();
        handleFetchTimeOutStaffNum();
        handleFetchSevereTimeOutStaffNum();
        handleFetchAbsenceStaffNum();
        handleFetchImportantAreaStaffNum();
        handleFetchLimitAreaStaffNum();
    }
    // 获取井下总人数
    private void handleFetchTotalNum(){
        api_url = App.url + "/attendance/getRtAttendanceStaff";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .header("Authorization",App.token)
                .url(api_url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", String.valueOf(e));
                Looper.prepare();
                App.toast.ToastMessageShort(String.valueOf(e));
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();//得到具体数据
                Log.i("总人数",responseData);
                Gson gson = new Gson();
                downHoleNum = gson.fromJson(responseData, DownHoleNumBean.class);
                if(downHoleNum.getCode()==200){
                    Looper.prepare();
                    person_sort1_num.setText(String.valueOf(downHoleNum.getData().getTotal()));
                    Looper.loop();
                }
            }
        });
    }
    // 获取井下超时人数
    private void handleFetchTimeOutStaffNum(){
        api_url = App.url + "/attendance/getOverTimeStaff";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .header("Authorization",App.token)
                .url(api_url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", String.valueOf(e));
                Looper.prepare();
                App.toast.ToastMessageShort(String.valueOf(e));
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();//得到具体数据
                Log.i("超时",responseData);
                Gson gson = new Gson();
                downHoleNum = gson.fromJson(responseData, DownHoleNumBean.class);
                if(downHoleNum.getCode()==200){
                    Looper.prepare();
                    person_sort2_num.setText(String.valueOf(downHoleNum.getData().getTotal()));
                    Looper.loop();
                }
            }
        });
    }
    // 获取井下严重超时人数
    private void handleFetchSevereTimeOutStaffNum(){
        api_url = App.url + "/attendance/getSeriousTimeStaff";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .header("Authorization",App.token)
                .url(api_url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", String.valueOf(e));
                Looper.prepare();
                App.toast.ToastMessageShort(String.valueOf(e));
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();//得到具体数据
                Log.i("严重超时",responseData);
                Gson gson = new Gson();
                downHoleNum = gson.fromJson(responseData, DownHoleNumBean.class);
                if(downHoleNum.getCode()==200){
                    Looper.prepare();
                    person_sort3_num.setText(String.valueOf(downHoleNum.getData().getTotal()));
                    Looper.loop();
                }
            }
        });
    }
    // 获取井下缺勤人数
    private void handleFetchAbsenceStaffNum(){
        api_url = App.url + "/attendance/getUnAttendanceStaff";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .header("Authorization",App.token)
                .url(api_url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", String.valueOf(e));
                Looper.prepare();
                App.toast.ToastMessageShort(String.valueOf(e));
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();//得到具体数据
                Log.i("缺勤",responseData);
                Gson gson = new Gson();
                downHoleNum = gson.fromJson(responseData, DownHoleNumBean.class);
                if(downHoleNum.getCode()==200){
                    Looper.prepare();
                    person_sort4_num.setText(String.valueOf(downHoleNum.getData().getTotal()));
                    Looper.loop();
                }
            }
        });
    }
    // 获取井下重点区域人数
    private void handleFetchImportantAreaStaffNum(){
        api_url = App.url + "/warningArea/findStaffNumByType?type=1";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .header("Authorization",App.token)
                .url(api_url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", String.valueOf(e));
                Looper.prepare();
                App.toast.ToastMessageShort(String.valueOf(e));
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();//得到具体数据
                Log.i("重点区域",responseData);
                Gson gson = new Gson();
                WarnAreaNumBean warnAreaNum = gson.fromJson(responseData, WarnAreaNumBean.class);
                if(warnAreaNum.getCode()==200){
                    Looper.prepare();
                    person_sort5_num.setText(String.valueOf(warnAreaNum.getData()));
                    Looper.loop();
                }
            }
        });
    }
    // 获取井下限制区域人数
    private void handleFetchLimitAreaStaffNum(){
        api_url = App.url + "/warningArea/findStaffNumByType?type=2";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .header("Authorization",App.token)
                .url(api_url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", String.valueOf(e));
                Looper.prepare();
                App.toast.ToastMessageShort(String.valueOf(e));
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();//得到具体数据
                Log.i("限制区域",responseData);
                Gson gson = new Gson();
                WarnAreaNumBean warnAreaNum = gson.fromJson(responseData, WarnAreaNumBean.class);
                if(warnAreaNum.getCode()==200){
                    Looper.prepare();
                    person_sort6_num.setText(String.valueOf(warnAreaNum.getData()));
                    Looper.loop();
                }
            }
        });
    }
    // 构建Runnable对象，在runnable中更新界面
//    private Runnable runnableUi =new  Runnable(){
//        @Override
//        public void run() {
//            //更新界面
//            int num = downHoleNum.getData().getTotal();
//                switch (staffNumType){
//                    case 1:
//                        person_sort1_num.setText(String.valueOf(num));
//                        break;
//                    case 2:
//                        person_sort2_num.setText(String.valueOf(num));
//                        break;
//                    case 3:
//                        person_sort3_num.setText(String.valueOf(num));
//                        break;
//                    case 4:
//                        person_sort4_num.setText(String.valueOf(num));
//                        break;
//                    case 5:
//                        person_sort5_num.setText(String.valueOf(num));
//                        break;
//                    case 6:
//                        person_sort6_num.setText(String.valueOf(num));
//                        break;
//                }
//        }
//    };
}