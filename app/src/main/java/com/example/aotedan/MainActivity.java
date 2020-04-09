package com.example.aotedan;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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