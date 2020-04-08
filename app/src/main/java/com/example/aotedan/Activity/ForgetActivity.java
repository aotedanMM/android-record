package com.example.aotedan.Activity;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aotedan.R;

public class ForgetActivity extends Activity implements View.OnClickListener {
    private TextView title_bar;
    private EditText forget_account;
    private EditText forget_code;
    private Button get_forget_code;
    private Button forget_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initView();
    }
    private void initView() {
        forget_account=(EditText) findViewById(R.id.forget_account);
        forget_code = (EditText)findViewById(R.id.forget_code);
        get_forget_code = (Button) findViewById(R.id.get_forget_code);
        forget_btn = (Button) findViewById(R.id.forget_btn);
        // 设置标题栏
        title_bar=findViewById(R.id.title_bar);
        title_bar.setText("忘记密码");

        setView();
    }
    private void setView() {
        get_forget_code.setOnClickListener(this);
        forget_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_forget_code:
                get_forget();
                break;
            case R.id.forget_btn:
                forget_btn();
                break;
        }
    }
    //    找回密码
    private void forget_btn(){
//        Intent intent = new Intent(LoginActivity.this,);
//        startActivity(intent);
    }
    //    获取验证码
    private void get_forget(){}

}
