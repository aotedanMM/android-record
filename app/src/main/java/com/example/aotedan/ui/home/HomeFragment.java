package com.example.aotedan.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aotedan.App.App;
import com.example.aotedan.R;
import com.example.aotedan.bean.DownHoleNumBean;
import com.example.aotedan.bean.WarnAreaNumBean;
import com.example.aotedan.network.NetworkRequest;
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
    private View home;
    private TextView person_sort1_num;
    private TextView person_sort2_num;
    private TextView person_sort3_num;
    private TextView person_sort4_num;
    private TextView person_sort5_num;
    private TextView person_sort6_num;
    private String api_url;
    private int totalNum;
    private int timeoutNum;
    private int seriousTimeNum;
    private int unAttendanceNum;
    private int importantNum;
    private int limitNum;
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
        setTitle();
        handleFetchAllStaffNum();
    }
    @SuppressLint("WrongConstant")
    private void setTitle() {
        TextView title_bar = (TextView)home.findViewById(R.id.title_bar);
        title_bar.setText("中科鑫合");
        ImageView title_back = home.findViewById(R.id.title_back);
        title_back.setVisibility(4);
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
        NetworkRequest.RequestGet(getActivity(), api_url, new com.example.aotedan.network.Request() {
            @Override
            public void success(String responseData) {
                Log.i("总人数",responseData);
                Gson gson = new Gson();
                downHoleNum = gson.fromJson(responseData, DownHoleNumBean.class);
                if(downHoleNum.getCode()==200){
                    totalNum = downHoleNum.getData().getTotal();
                    handler.post(setTotalNum);
                }
            }
            @Override
            public void error(String error) {
                Log.i("error",error);
            }
        });
    }
    // 获取井下超时人数
    private void handleFetchTimeOutStaffNum(){
        api_url = App.url + "/attendance/getOverTimeStaff";
        NetworkRequest.RequestGet(getActivity(), api_url, new com.example.aotedan.network.Request() {
            @Override
            public void success(String responseData) {
                Log.i("超时人数",responseData);
                Gson gson = new Gson();
                downHoleNum = gson.fromJson(responseData, DownHoleNumBean.class);
                if(downHoleNum.getCode()==200){
                    timeoutNum = downHoleNum.getData().getTotal();
                    handler.post(setTimeoutNum);
                }
            }
            @Override
            public void error(String error) {
                Log.i("error",error);
            }
        });
    }
    // 获取井下严重超时人数
    private void handleFetchSevereTimeOutStaffNum(){
        api_url = App.url + "/attendance/getSeriousTimeStaff";
        NetworkRequest.RequestGet(getActivity(), api_url, new com.example.aotedan.network.Request() {
            @Override
            public void success(String responseData) {
                Log.i("严重超时",responseData);
                Gson gson = new Gson();
                downHoleNum = gson.fromJson(responseData, DownHoleNumBean.class);
                if(downHoleNum.getCode()==200){
                    seriousTimeNum = downHoleNum.getData().getTotal();
                    handler.post(setSeriousTimeNum);
                }
            }
            @Override
            public void error(String error) {
                Log.i("error",error);
            }
        });
    }
    // 获取井下缺勤人数
    private void handleFetchAbsenceStaffNum(){
        api_url = App.url + "/attendance/getUnAttendanceStaff";
        NetworkRequest.RequestGet(getActivity(), api_url, new com.example.aotedan.network.Request() {
            @Override
            public void success(String responseData) {
                Log.i("缺勤人数",responseData);
                Gson gson = new Gson();
                downHoleNum = gson.fromJson(responseData, DownHoleNumBean.class);
                if(downHoleNum.getCode()==200){
                    unAttendanceNum = downHoleNum.getData().getTotal();
                    handler.post(setUnAttendanceNum);
                }
            }
            @Override
            public void error(String error) {
                Log.i("error",error);
            }
        });
    }

    // 获取井下重点区域人数
    private void handleFetchImportantAreaStaffNum(){
        api_url = App.url + "/warningArea/findStaffNumByType?type=1";
        NetworkRequest.RequestGet(getActivity(), api_url, new com.example.aotedan.network.Request() {
            @Override
            public void success(String responseData) {
                Log.i("重点区域",responseData);
                Gson gson = new Gson();
                warnAreaNum = gson.fromJson(responseData, WarnAreaNumBean.class);
                if(warnAreaNum.getCode()==200){
                    importantNum = warnAreaNum.getData();
                    handler.post(setImportantNum);
                }
            }
            @Override
            public void error(String error) {
                Log.i("error",error);
            }
        });
    }
    // 获取井下限制区域人数
    private void handleFetchLimitAreaStaffNum(){
        api_url = App.url + "/warningArea/findStaffNumByType?type=2";
        NetworkRequest.RequestGet(getActivity(), api_url, new com.example.aotedan.network.Request() {
            @Override
            public void success(String responseData) {
                Log.i("限制区域",responseData);
                Gson gson = new Gson();
                warnAreaNum = gson.fromJson(responseData, WarnAreaNumBean.class);
                if(warnAreaNum.getCode()==200){
                    limitNum = warnAreaNum.getData();
                    handler.post(setLimitNum);
                }
            }
            @Override
            public void error(String error) {
                Log.i("error",error);
            }
        });
    }
    // 更新井下总人数
    private Runnable setTotalNum =new  Runnable(){
        @Override
        public void run() {
            //更新界面
            person_sort1_num.setText(String.valueOf(totalNum));
        }
    };
    // 更新井下超时人数
    private Runnable setTimeoutNum =new  Runnable(){
        @Override
        public void run() {
            //更新界面
            person_sort2_num.setText(String.valueOf(timeoutNum));
        }
    };
    // 更新井下严重超时人数
    private Runnable setSeriousTimeNum =new  Runnable(){
        @Override
        public void run() {
            //更新界面
            person_sort3_num.setText(String.valueOf(seriousTimeNum));
        }
    };
    // 更新井下缺勤人数
    private Runnable setUnAttendanceNum =new  Runnable(){
        @Override
        public void run() {
            //更新界面
            person_sort4_num.setText(String.valueOf(unAttendanceNum));
        }
    };
    // 更新井下重点区域
    private Runnable setImportantNum =new  Runnable(){
        @Override
        public void run() {
            //更新界面
            person_sort5_num.setText(String.valueOf(importantNum));
        }
    };
    // 更新井下限制区域
    private Runnable setLimitNum =new  Runnable(){
        @Override
        public void run() {
            //更新界面
            person_sort6_num.setText(String.valueOf(limitNum));
        }
    };

}