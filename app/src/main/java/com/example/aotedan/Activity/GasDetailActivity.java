package com.example.aotedan.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.aotedan.R;

public class GasDetailActivity extends Activity implements View.OnClickListener {
    private EditText search_edit;
    private Button search_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gas_detail);
        initView();
        initData();
    }
    // 初始化视图
    private void initView() {
        search_edit = (EditText) findViewById(R.id.search_edit);
        search_btn = (Button) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(this);
    }
    // 点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_btn:
                handleSearchStaffData();
                break;
        }
    }
    // 根据员工姓名查询员工定位信息
    private void handleSearchStaffData() {

    }
    // 获取数据
    private void initData() {

    }
}
