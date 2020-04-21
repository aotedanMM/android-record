package com.example.aotedan.Activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.aotedan.utils.SharedHelper;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Activity implements View.OnClickListener {
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
        user_account = findViewById(R.id.user_name);
        user_psd = findViewById(R.id.user_psd);
        login_btn = findViewById(R.id.login_btn);
        register = findViewById(R.id.register);
        forget = findViewById(R.id.forget);
        // 设置标题栏（登录）
        title_bar = findViewById(R.id.title_bar);
        title_bar.setText("登录");
        mContext = getApplicationContext();
        sh = new SharedHelper(mContext);
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
                Log.i("onClick", "login");
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
    //    登录
    private void login() {
        name = user_account.getText().toString().trim();
        psd = user_psd.getText().toString().trim();
//        if (name.equals("")) {
//            App.toast.ToastMessageShort("请输入账号");
//            return;
//        }
//        if (psd.equals("")) {
//            App.toast.ToastMessageShort("请输入密码");
//            return;
//        }
        Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
        startActivity(intent);
        // 保存用户信息到本地
//        String user_name = loginDataBean.getData().getStaffName();
//        String user_phone = loginDataBean.getData().getStaffPhone();
        sh.save("王大锤","1573333221");

//        new Thread(new Runnable() {//在这个方法中同样还是先开启了一个子线程
//            @Override
//            public void run() {
//                try {
//                    MediaType mediaType  = MediaType.parse("application/json; charset=utf-8");
//                    JSONObject json = new JSONObject();
//                    // 13536951364 、13243210010
//                    json.put("wxUserAccount","13243210010");
//                    json.put("wxUserPassword","123456");
//                    String http_url = "http://192.168.1.50:8080/v1/api/wx/login";
//                    OkHttpClient client = new OkHttpClient();
//                    RequestBody requestBody = FormBody.create(mediaType , json.toString());
//                    Request request = new Request.Builder()
//                            .header("Authorization","13243210010")
//                            .url(http_url)
//                            .post(requestBody)
//                            .build();
//                    Response response = client.newCall(request).execute();//接收服务器返回的数据
//                    String responseData = response.body().string();//得到具体数据
//                    Log.i("resp",responseData);
//                    Gson gson = new Gson();
//                    loginDataBean = gson.fromJson(responseData,LoginDataBean.class);
//                    if(loginDataBean.getCode() == 200) {
//                        Looper.prepare();
//                        App.toast.ToastMessageShort(loginDataBean.getMsg());
//                        Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
//                        startActivity(intent);
//                        // 保存用户信息到本地
//                        String user_name = loginDataBean.getData().getStaffName();
//                        String user_phone = loginDataBean.getData().getStaffPhone();
//                        sh.save(user_name,user_phone);
//                        Looper.loop();
//
//                    } else {
//                        Looper.prepare();
//                        App.toast.ToastMessageShort(loginDataBean.getMsg());
//                        Looper.loop();
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
    private void register() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
    private void forgetPsd() {

        Intent intent = new Intent(LoginActivity.this, ForgetActivity.class);
        startActivity(intent);
    }
}