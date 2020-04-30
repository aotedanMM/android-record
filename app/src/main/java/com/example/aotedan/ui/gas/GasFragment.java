package com.example.aotedan.ui.gas;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aotedan.Activity.GasDetailActivity;
import com.example.aotedan.Adapter.GasListAdapter;
import com.example.aotedan.App.App;
import com.example.aotedan.Entity.GasEntity;
import com.example.aotedan.R;
import com.example.aotedan.bean.GasDataBean;
import com.example.aotedan.network.NetworkRequest;
import com.example.aotedan.utils.InputMethodUtils;
import com.example.aotedan.view.MyScrollview;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

//import java.util.Map;




public class GasFragment extends Fragment implements View.OnClickListener,MyScrollview.scrollListener {
    private View gas;
    private LinearLayout sort_btn1;
    private LinearLayout sort_btn2;
    private LinearLayout sort_btn3;
    private LinearLayout sort_btn4;
    private LinearLayout sort_btn5;
    private Handler handler;
    private EditText gas_staff_edit;
    private MyScrollview myScroll;
    private Button search_btn;
    private GasDataBean gasDataBean;
    private RecyclerView collect_recyclerView;
    public RecyclerView mCollectRecyclerView;//定义RecyclerView
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<GasEntity> gasEntityArrayList = new ArrayList<GasEntity>();
    //自定义recyclerveiw的适配器
    private GasListAdapter mCollectRecyclerAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gas = inflater.inflate(R.layout.fragment_gas, container, false);
        handler = new Handler();
        initView();
        setTitle();
        setView();
        initData(null);
        return gas;
    }
    private void initView(){
        sort_btn1 = gas.findViewById(R.id.sort_btn1);
        sort_btn2 = gas.findViewById(R.id.sort_btn2);
        sort_btn3 = gas.findViewById(R.id.sort_btn3);
        sort_btn4 = gas.findViewById(R.id.sort_btn4);
        sort_btn5 = gas.findViewById(R.id.sort_btn5);
        gas_staff_edit = gas.findViewById(R.id.gas_staff_edit);
        search_btn = gas.findViewById(R.id.search_btn);
        collect_recyclerView = gas.findViewById(R.id.collect_recyclerView);
        myScroll = gas.findViewById(R.id.myScroll);
    }
    private void setView() {
        search_btn.setOnClickListener(this);
        sort_btn1.setOnClickListener(this);
        sort_btn2.setOnClickListener(this);
        sort_btn3.setOnClickListener(this);
        sort_btn4.setOnClickListener(this);
        sort_btn5.setOnClickListener(this);
        myScroll.setScroll(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        collect_recyclerView.setLayoutManager(linearLayoutManager);
//        collect_recyclerView.setHasFixedSize(true);
//        collect_recyclerView.setNestedScrollingEnabled(false);
    }
    @SuppressLint("WrongConstant")
    private void setTitle() {
        TextView title_bar = gas.findViewById(R.id.title_bar);
        title_bar.setText("气体监测");
        ImageView title_back = gas.findViewById(R.id.title_back);
        title_back.setVisibility(4);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_btn:
                handleFetchStaffGasData();
                break;
            case R.id.sort_btn1:
                handleFetchGasDetails("ch4");
                break;
            case R.id.sort_btn2:
                handleFetchGasDetails("co");
                break;
            case R.id.sort_btn3:
                handleFetchGasDetails("o2");
                break;
            case R.id.sort_btn4:
                handleFetchGasDetails("temperature");
                break;
            case R.id.sort_btn5:
                handleFetchGasDetails("humidity");
                break;
        }
    }
    private void handleFetchGasDetails(String type){
        Intent intent = new Intent(getActivity(), GasDetailActivity.class);
        intent.putExtra("key",type);
        startActivity(intent);
    }
    // 根据员工姓名查找气体数据
    private void handleFetchStaffGasData() {
        // 每次查询先清空原有的数据、清空列表
        gasEntityArrayList.clear();
        mCollectRecyclerAdapter.notifyDataSetChanged();
        String staff_name = gas_staff_edit.getText().toString().trim();
        initData(staff_name);
        InputMethodUtils.showOrHide(getActivity()); // 显示、隐藏输入法
    }
    private void initData(String name) {
        String url = App.url+ "/gas/findRtGasInfoByStaffName";
        Map<String ,String> map = new ArrayMap<>();
        map.put("page","1");
        map.put("limit","20");
        if(name != null) {
            map.put("staffName",name);
        }
        NetworkRequest.RequestGetParams(getActivity(), url, map,new com.example.aotedan.network.Request() {
            @Override
            public void success(String resp) {
                Log.i("gas",resp);
                Gson gson = new Gson();
                gasDataBean = gson.fromJson(resp,GasDataBean.class);
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
    // 清空recyclerView
    private Runnable resetRecyclerView = new  Runnable(){
        @Override
        public void run() {
            gasEntityArrayList.clear();
            mCollectRecyclerAdapter.notifyDataSetChanged();
        }
    };
    /**
     * TODO 对recycleview进行配置
     */
    private void initRecyclerView() {
        //获取RecyclerView
        mCollectRecyclerView=(RecyclerView)gas.findViewById(R.id.collect_recyclerView);
        //创建adapter
        mCollectRecyclerAdapter = new GasListAdapter(getActivity(), gasEntityArrayList);
        //给RecyclerView设置adapter
        mCollectRecyclerView.setAdapter(mCollectRecyclerAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mCollectRecyclerAdapter.setOnItemClickListener(new GasListAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, GasEntity data) {
                //此处进行监听事件的业务处理
                Toast.makeText(getActivity(),"我是item",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void scroll(int yy) {

    }
}
