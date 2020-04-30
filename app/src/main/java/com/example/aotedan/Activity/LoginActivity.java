package com.example.aotedan.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.aotedan.App.App;
import com.example.aotedan.IndexActivity;
import com.example.aotedan.R;
import com.example.aotedan.bean.LoginDataBean;
import com.example.aotedan.network.NetworkRequest;
import com.example.aotedan.utils.SharedHelper;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;



public class LoginActivity extends Activity implements View.OnClickListener {
    private Handler handler;
    private TextView title_bar;
    private LoginDataBean loginDataBean;
    private EditText user_account;
    private EditText user_psd;
    private Button login_btn;
    private Button register;
    private Button forget;
    private String name;
    private String psd;
    private SharedHelper sh;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView() {
        // 设置标题栏（登录）
        title_bar = findViewById(R.id.title_bar);
        title_bar.setText("登录");
        user_account = findViewById(R.id.user_name);
        user_psd = findViewById(R.id.user_psd);
        login_btn = findViewById(R.id.login_btn);
        register = findViewById(R.id.register);
        forget = findViewById(R.id.forget);
        mContext = getApplicationContext();
        sh = new SharedHelper(mContext);
        handler = new Handler();
        setView();
    }
    private void setView() {
        login_btn.setOnClickListener(this);
        register.setOnClickListener(this);
        forget.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                login();
                break;
            case R.id.register:
                register();
                break;
            case R.id.forget:
                forgetPsd();
                break;
        }
    }
    public void login() {
        name = user_account.getText().toString().trim();
        psd = user_psd.getText().toString().trim();
        if (name.equals("")) {
            App.toast.ToastMessageShort("请输入账号");
            return;
        }
        if (psd.equals("")) {
            App.toast.ToastMessageShort("请输入密码");
            return;
        }
        JSONObject json = new JSONObject();
        try {
            json.put("account","admin");
            json.put("passWord","123456");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = App.url + "/login";
        NetworkRequest.RequestPostParams(LoginActivity.this,url, json, new com.example.aotedan.network.Request() {
            @Override
            public void success(String responseData) {
                Log.i("resp",String.valueOf(responseData));
                Gson gson = new Gson();
                loginDataBean = gson.fromJson(responseData, LoginDataBean.class);
                if(loginDataBean.getCode()==200){
                    // 保存用户信息到本地
                    String user_name = loginDataBean.getData().getAccount();
                    String user_phone = loginDataBean.getData().getPhone();
                    String token = loginDataBean.getData().getToken();
                    sh.save(user_name,user_phone,token);
                    App.token = loginDataBean.getData().getToken();
                    // 跳转到首页
                    handler.post(activityToIndex);
                }
            }
            @Override
            public void error(String error) {
                Log.i("error",error);
            }
        });
    }
    private void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    private void forgetPsd() {
        Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
        startActivity(intent);
    }
    //页面跳转
    private Runnable activityToIndex =new  Runnable(){
        @Override
        public void run() {
            Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
            startActivity(intent);
        }
    };
}