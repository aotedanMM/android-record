package com.example.aotedan;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.aotedan.Activity.LoginActivity;

public class MainActivity extends Activity implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Fragment fragments;
    private TextView title_bar;
    private String data[] = {"aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd"};//假数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listview);//在视图中找到ListView
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,data);//新建并配置ArrayAapeter
        listView.setAdapter(adapter);
        initView();
    }
    private void initView(){
        radioGroup = findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(this);
        // 默认选中按钮组的第一项
        radioGroup.check(R.id.bottom_btn1);
        radioButton = findViewById(R.id.bottom_btn1);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}