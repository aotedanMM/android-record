package com.example.aotedan.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aotedan.Adapter.GasListAdapter;
import com.example.aotedan.App.App;
import com.example.aotedan.Entity.GasEntity;
import com.example.aotedan.Event.EventMessage;
import com.example.aotedan.R;
import com.example.aotedan.bean.GasDataBean;
import com.example.aotedan.network.NetworkRequest;
import com.example.aotedan.ui.gas.GasFragment;
import com.example.aotedan.utils.InputMethodUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Map;

public class GasDetailActivity extends Activity implements View.OnClickListener {
    private TextView ch4;
    private TextView co;
    private TextView o2;
    private TextView temperature;
    private TextView humidity;
    private EditText search_edit;
    private Button search_btn;
    private Handler handler;
    private GasDataBean gasDataBean;
    public RecyclerView mCollectRecyclerView;//定义RecyclerView
    private ArrayList<GasEntity> gasEntityArrayList = new ArrayList<GasEntity>();
    private GasListAdapter mCollectRecyclerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_detail);
        initView();
        setTitle();
        initData(null);
    }
    // 初始化视图
    private void initView() {
        handler = new Handler();
        search_edit = (EditText) findViewById(R.id.search_edit);
        search_btn = (Button) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(this);
        ch4 = findViewById(R.id.gas_ch4);
        co = findViewById(R.id.gas_co);
        o2 = findViewById(R.id.gas_o2);
        temperature = findViewById(R.id.gas_temperature);
        humidity = findViewById(R.id.gas_humidity);
        setGasVisible();
    }
    private void setGasVisible() {
        Intent intent2 = getIntent();
        String gasType = intent2.getStringExtra("key");
        Log.i("gasType",gasType);
        switch (gasType) {
            case "ch4":
                ch4.setVisibility(View.VISIBLE);
                break;
            case "co":
                co.setVisibility(View.VISIBLE);
                break;
            case "o2":
                o2.setVisibility(View.VISIBLE);
                break;
            case "temperature":
                temperature.setVisibility(View.VISIBLE);
                break;
            case "humidity":
                humidity.setVisibility(View.VISIBLE);
                break;
        }

    }
    private void setTitle() {
        TextView title_bar = findViewById(R.id.title_bar);
        title_bar.setText("气体详情");
        ImageView title_back = findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
    }
    // 点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_btn:
                handleSearchStaffData();
                break;
            case R.id.title_back:
                GasDetailActivity.this.finish();
                break;
        }
    }
    // 根据员工姓名查询员工定位信息
    private void handleSearchStaffData() {
        String staffName = search_edit.getText().toString().trim();
        initData(staffName);
        InputMethodUtils.showOrHide(GasDetailActivity.this); // 显示、隐藏输入法
    }
    private void initData(String name) {
        String url = App.url+ "/gas/findRtGasInfoByStaffName";
        Map<String ,String> map = new ArrayMap<>();
        map.put("page","1");
        map.put("limit","20");
        if(name != null) {
            map.put("staffName",name);
        }
        NetworkRequest.RequestGetParams(GasDetailActivity.this, url, map,new com.example.aotedan.network.Request() {
            @Override
            public void success(String resp) {
                Log.i("gas",resp);
                Gson gson = new Gson();
                gasDataBean = gson.fromJson(resp, GasDataBean.class);
                if(gasDataBean.getCode() == 200) {
                    for (int i = 0; i < gasDataBean.getData().getList().size(); i++) {
                        GasDataBean.DataBean.ListsBean b = gasDataBean.getData().getList().get(i);
                        GasEntity gasEntity = new GasEntity();
                        gasEntity.setCh4(b.getCh4());
                        gasEntity.setCo(b.getCo());
                        gasEntity.setO2(b.getO2());
                        gasEntity.setHumidity(b.getHumidity());
                        gasEntity.setTemperature(b.getTemperature());
                        gasEntity.setStaff_name(b.getStaffname());
                        gasEntity.setGroup_name(b.getGroupName());
                        gasEntity.setTemppositionname(b.getTemppositionname());
                        gasEntity.setCreatetime(b.getCreatetime());
                        gasEntityArrayList.add(gasEntity);
                    }
                    handler.post(loadRecyclerView);
                }
            }
            @Override
            public void error(String error) {
                Log.i("error",error);
            }
        });
    }
    private Runnable loadRecyclerView = new  Runnable(){
        @Override
        public void run() {
            initRecyclerView();
        }
    };
    private void initRecyclerView() {
        //获取RecyclerView
        mCollectRecyclerView = findViewById(R.id.collect_recyclerView);
        //创建adapter
        mCollectRecyclerAdapter = new GasListAdapter(GasDetailActivity.this, gasEntityArrayList);
        //给RecyclerView设置adapter
        mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(GasDetailActivity.this, LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(GasDetailActivity.this,DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mCollectRecyclerAdapter.setOnItemClickListener(new GasListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, GasEntity data) {
                //此处进行监听事件的业务处理
                Toast.makeText(GasDetailActivity.this,"我是item",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
