package com.example.aotedan.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.aotedan.R;
import com.example.aotedan.bean.DownHoleNumBean;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment<Int> extends Fragment implements View.OnClickListener{
    private Handler handler;
    private DownHoleNumBean downHoleNum;
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
                Gson gson = new Gson();
                downHoleNum = gson.fromJson(responseData, DownHoleNumBean.class);
                if(downHoleNum.getCode()==200){
                    handler.post(runnableUi);
                }
            }
        });
    }
    // 构建Runnable对象，在runnable中更新界面
    private Runnable runnableUi =new  Runnable(){
        @Override
        public void run() {
            //更新界面
                person_sort1_num.setText(downHoleNum.getData().getSumStaff());
                person_sort2_num.setText(downHoleNum.getData().getOverTimeStaff());
                person_sort3_num.setText(downHoleNum.getData().getSeriousTimeStaff());
                person_sort4_num.setText(downHoleNum.getData().getUnAttendanceStaff());
                person_sort5_num.setText(downHoleNum.getData().getImportantStaff());
                person_sort6_num.setText(downHoleNum.getData().getLimitStaff());
        }
    };

}