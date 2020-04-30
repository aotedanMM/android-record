package com.example.aotedan.ui.person;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aotedan.Activity.GasDetailActivity;
import com.example.aotedan.Adapter.GasListAdapter;
import com.example.aotedan.Adapter.StaffListAdapter;
import com.example.aotedan.App.App;
import com.example.aotedan.Entity.GasEntity;
import com.example.aotedan.Entity.StaffEntity;
import com.example.aotedan.R;
import com.example.aotedan.bean.GasDataBean;
import com.example.aotedan.bean.StaffDataBean;
import com.example.aotedan.network.NetworkRequest;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PersonFragment extends Fragment implements View.OnClickListener{
    private View person;
    private Handler handler;
    private View sort_btn1;
    private View sort_btn2;
    private View sort_btn3;
    private View sort_btn4;
    private View sort_btn5;
    private View sort_btn6;
    private EditText staff_edit;
    private Button search_btn;
    private StaffDataBean staffDataBean;
    public RecyclerView mCollectRecyclerView;//定义RecyclerView
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<StaffEntity> staffEntityArrayList = new ArrayList<StaffEntity>();
    //自定义recyclerveiw的适配器
    private StaffListAdapter mCollectRecyclerAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        person = inflater.inflate(R.layout.fragment_person, container, false);
        initView();
        setTitle();
        initData(null);
        return person;
    }
    private void initView(){
        handler = new Handler();
        search_btn = person.findViewById(R.id.search_btn);
        staff_edit = person.findViewById(R.id.staff_edit);
        sort_btn1 = person.findViewById(R.id.sort_btn1);
        sort_btn2 = person.findViewById(R.id.sort_btn2);
        sort_btn3 = person.findViewById(R.id.sort_btn3);
        sort_btn4 = person.findViewById(R.id.sort_btn4);
        sort_btn5 = person.findViewById(R.id.sort_btn5);
        sort_btn6 = person.findViewById(R.id.sort_btn6);
        search_btn.setOnClickListener(this);
        sort_btn1.setOnClickListener(this);
        sort_btn2.setOnClickListener(this);
        sort_btn3.setOnClickListener(this);
        sort_btn4.setOnClickListener(this);
        sort_btn5.setOnClickListener(this);
        sort_btn6.setOnClickListener(this);
    }
    @SuppressLint("WrongConstant")
    private void setTitle() {
        TextView title_bar = person.findViewById(R.id.title_bar);
        title_bar.setText("人员监测");
        ImageView title_back = person.findViewById(R.id.title_back);
        title_back.setVisibility(4);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_btn:
                handleSearchStaffData();
                break;
            case R.id.sort_btn1:
                activityToDetail(1);
                break;
            case R.id.sort_btn2:
                activityToDetail(2);
                break;
            case R.id.sort_btn3:
                activityToDetail(3);
                break;
            case R.id.sort_btn4:
                activityToDetail(4);
                break;
            case R.id.sort_btn5:
                activityToDetail(5);
                break;
            case R.id.sort_btn6:
                activityToDetail(6);
                break;
        }
    }
    // 根据员工姓名查找员工信息
    private void handleSearchStaffData() {
        staffEntityArrayList.clear();
        mCollectRecyclerAdapter.notifyDataSetChanged();
        String search_name = staff_edit.getText().toString().trim();
        initData(search_name);
    }
    // 跳转到详情页
    private void activityToDetail( int type) {
        Intent intent = new Intent(getActivity(), GasDetailActivity.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    // 获取数据
    private  void initData(String staffName) {
        String url = App.url +  "attendance/getRtAttendanceStaff";
        Map<String,String> map = new ArrayMap<>();
        map.put("limit","20");
        map.put("page","1");
        if(staffName != null) {
            map.put("staffName","");
        }
        NetworkRequest.RequestGetParams(getActivity(), url, map, new com.example.aotedan.network.Request() {
            @Override
            public void success(String resp) {
                Log.i("resp",resp);
                Gson gson = new Gson();
                staffDataBean = gson.fromJson(resp, StaffDataBean.class);
                if(staffDataBean.getCode() == 200) {
                    for (int i = 0; i < staffDataBean.getData().getList().size(); i++) {
                        StaffDataBean.DataBean.ListsBean b = staffDataBean.getData().getList().get(i);
                        StaffEntity staffEntity = new StaffEntity();
                        staffEntity.setStaffName(b.getStaffName());
                        staffEntity.setDeptName(b.getDeptName());
                        staffEntity.setInOreTime(b.getInOreTime());
                        staffEntity.setFinalTime(b.getFinalTime());
                        staffEntity.setTimeLong(b.getTimeLong());
                        staffEntityArrayList.add(staffEntity);
                    }
                    handler.post(loadRecyclerView);
                } else if(staffDataBean.getCode() == 111) {
                    handler.post(resetRecyclerView);
                }
            }
            @Override
            public void error(String error) {
                Log.d("error",error);
            }
        });
    }
    // 构建Runnable对象，在runnable中更新界面
    private Runnable loadRecyclerView = new  Runnable(){
        @Override
        public void run() {
            //更新界面
            initRecyclerView();
        }
    };
    // 构建Runnable对象，在runnable中更新界面
    private Runnable resetRecyclerView = new  Runnable(){
        @Override
        public void run() {
            //更新界面
            initRecyclerView();
        }
    };
    private void initRecyclerView() {
        //获取RecyclerView
        mCollectRecyclerView=(RecyclerView)person.findViewById(R.id.collect_recyclerView);
        //创建adapter
        mCollectRecyclerAdapter = new StaffListAdapter(getActivity(), staffEntityArrayList);
        //给RecyclerView设置adapter
        mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mCollectRecyclerAdapter.setOnItemClickListener(new StaffListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, StaffEntity data) {
                //此处进行监听事件的业务处理
                Toast.makeText(getActivity(),"我是item",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
