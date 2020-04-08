package com.example.aotedan.Activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.aotedan.MainActivity;
import com.example.aotedan.R;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private TextView title_bar;
    private EditText register_account;
    private EditText register_psd;
    private EditText register_psd_again;
    private Button register_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView() {
        register_account=(EditText) findViewById(R.id.register_account);
        register_psd = (EditText)findViewById(R.id.register_psd);
        register_psd_again = (EditText)findViewById(R.id.register_psd_again);
        register_btn = (Button) findViewById(R.id.register_btn);
        // 设置标题栏
        title_bar = findViewById(R.id.title_bar);
        title_bar.setText("注册");
        setView();
    }
    private void setView() {
        register_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                register();
                break;
        }
    }
    //    注册
    private void register(){
//        Intent intent = new Intent(LoginActivity.this,);
//        startActivity(intent);
    }

}
