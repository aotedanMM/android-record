package com.example.aotedan.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aotedan.R;

public class PersonDetailActivity extends Activity implements View.OnClickListener {
    private EditText search_edit;
    private Button search_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        initView();
        setTitle();
        initData();
    }
    // 初始化视图
    private void initView() {
        search_edit = (EditText) findViewById(R.id.search_edit);
        search_btn = (Button) findViewById(R.id.search_btn);
        search_btn.setOnClickListener(this);
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
                PersonDetailActivity.this.finish();
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
