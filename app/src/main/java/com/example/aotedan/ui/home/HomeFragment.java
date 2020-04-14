package com.example.aotedan.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.aotedan.App.App;
import com.example.aotedan.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment<Int> extends Fragment implements View.OnClickListener{
    private Handler handler;
    private JSONObject personNumData;
    private String sumStaff;
    private String overTimeStaff;
    private String seriousTimeStaff;
    private String unAttendanceStaff;
    private String importantStaff;
    private String limitStaff;
    private View root;
    private TextView person_sort1_num;
    private TextView person_sort2_num;
    private TextView person_sort3_num;
    private TextView person_sort4_num;
    private TextView person_sort5_num;
    private TextView person_sort6_num;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        //创建属于主线程的handler
        handler=new Handler();
        return root;
    }
    public void onStart() {
        super.onStart();
        initView();
    }
    // 初始化控件
    private void initView() {
        person_sort1_num = (TextView) root.findViewById(R.id.person_sort1_num);
        person_sort2_num = (TextView) root.findViewById(R.id.person_sort2_num);
        person_sort3_num = (TextView) root.findViewById(R.id.person_sort3_num);
        person_sort4_num = (TextView) root.findViewById(R.id.person_sort4_num);
        person_sort5_num = (TextView) root.findViewById(R.id.person_sort5_num);
        person_sort6_num = (TextView) root.findViewById(R.id.person_sort6_num);
        fetchStaffInfo();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.test_btn:
//                App.toast.ToastMessageShort("你点击了test按钮");
        }
    }
    private void fetchStaffInfo(){
        String url = "http://192.168.1.50:8080/v1/api/wx/getOreStaffInfo";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .header("Authorization","13243210010")
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("TAG", "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();//得到具体数据
                Log.i("resp",responseData);
                try {
                    JSONObject jsonObject = new JSONObject(responseData);
                    int code = jsonObject.optInt("code");// code
                    String msg = jsonObject.getString("msg");// msg
                    personNumData = jsonObject.getJSONObject("data");
                    sumStaff = personNumData.getString("sumStaff");
                    overTimeStaff = personNumData.getString("overTimeStaff");
                    seriousTimeStaff = personNumData.getString("seriousTimeStaff");
                    unAttendanceStaff = personNumData.getString("unAttendanceStaff");
                    importantStaff = personNumData.getString("importantStaff");
                    limitStaff = personNumData.getString("limitStaff");
                    Log.i("personNumData",String.valueOf(personNumData));
                    if(code == 200) {
                        handler.post(runnableUi);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    // 构建Runnable对象，在runnable中更新界面
    private Runnable runnableUi =new  Runnable(){
        @Override
        public void run() {
            //更新界面
                person_sort1_num.setText(sumStaff);
                person_sort2_num.setText(overTimeStaff);
                person_sort3_num.setText(seriousTimeStaff);
                person_sort4_num.setText(unAttendanceStaff);
                person_sort5_num.setText(importantStaff);
                person_sort6_num.setText(limitStaff);
        }
    };

}