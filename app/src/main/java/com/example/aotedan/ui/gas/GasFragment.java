package com.example.aotedan.ui.gas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aotedan.Adapter.GasListAdapter;
import com.example.aotedan.App.App;
import com.example.aotedan.Entity.GasEntity;
import com.example.aotedan.R;
import com.example.aotedan.bean.GasDataBean;
import com.example.aotedan.network.NetworkRequest;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GasFragment extends Fragment implements View.OnClickListener {
    private View gas;
    private Handler handler;
    private EditText gas_staff_edit;
    private Button search_btn;
    private GasDataBean gasDataBean;
    public RecyclerView mCollectRecyclerView;//定义RecyclerView
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<GasEntity> gasEntityArrayList = new ArrayList<GasEntity>();
    //自定义recyclerveiw的适配器
    private GasListAdapter mCollectRecyclerAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gas = inflater.inflate(R.layout.fragment_gas, container, false);
        handler = new Handler();
        initView();
        initData(null);
        //模拟数据
        //对recycleview进行配置
//        initRecyclerView();
        return gas;
    }
    private void initView(){
        TextView title_bar = gas.findViewById(R.id.title_bar);
        title_bar.setText("气体监测");
        gas_staff_edit = gas.findViewById(R.id.gas_staff_edit);
        search_btn = gas.findViewById(R.id.search_btn);
        search_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_btn:
                handleFetchStaffGasData();
                break;
        }
    }
    // 根据员工姓名查找气体数据
    private void handleFetchStaffGasData() {
        String search_name = gas_staff_edit.getText().toString().trim();
        if(search_name.equals("")) {
            App.toast.ToastMessageShort("请输入要查询的员工姓名");
            initData(null);
            return;
        } else {
            // 每次查询先清空原有的数据
            gasEntityArrayList.clear();
            mCollectRecyclerAdapter.notifyDataSetChanged();
            initData(search_name);
        }
    }

    private void initData(String params) {
        String url = "http://192.168.1.50:8080/v1/api/gas/findRtGasInfoByStaffName";
        NetworkRequest.RequestGetParams(getActivity(), url, 1, 1, 20, new com.example.aotedan.network.Request() {
            @Override
            public void success(String resp) {
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
                } else if(gasDataBean.getCode() == 111){
                    handler.post(resetRecyclerView);
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

}
