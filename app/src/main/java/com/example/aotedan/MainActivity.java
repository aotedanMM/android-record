package com.example.aotedan;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.aotedan.Activity.LoginActivity;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button index_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        index_login = findViewById(R.id.index_login);
        setView();
    }
    private void setView(){
        index_login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Log.i("onClick","register");
        switch (v.getId()) {
            case R.id.index_login:
                goLogin();
                break;
        }
    }
    private void goLogin() {
//        Log.i("login","login");
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}