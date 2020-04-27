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
import com.example.aotedan.utils.SSLSocketClient;
import com.example.aotedan.utils.SharedHelper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
//        handleSSLHandshake();
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
    private void login() {
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
        MediaType mediaType  = MediaType.parse("application/json; charset=utf-8");
        JSONObject json = new JSONObject();
        try {
            json.put("account",name);
            json.put("passWord",psd);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = "https://192.168.1.50:8443/v1/api/login";
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .sslSocketFactory(SSLSocketClient.SSLSocketFactory())//配置
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
                .build();
        RequestBody requestBody = FormBody.create(mediaType , json.toString());
        final Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("TAG", String.valueOf(e));
                Looper.prepare();
                App.toast.ToastMessageShort(String.valueOf(e));
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();//得到具体数据
                Log.i("resp",responseData);
                Gson gson = new Gson();
                loginDataBean = gson.fromJson(responseData, LoginDataBean.class);
                if(loginDataBean.getCode()==200){
                    handler.post(activityToIndex);
//                     保存用户信息到本地
                    String user_name = loginDataBean.getData().getUser_name();
                    String user_phone = loginDataBean.getData().getPhone();
                    String token = loginDataBean.getData().getToken();
                    sh.save(user_name,user_phone,token);
                } else {
                    Looper.prepare();
                    App.toast.ToastMessageShort(loginDataBean.getMsg());
                    Looper.loop();
                }
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
    // 更新视图
    private Runnable activityToIndex =new  Runnable(){
        @Override
        public void run() {
            //页面跳转
            Intent intent = new Intent(LoginActivity.this, IndexActivity.class);
            startActivity(intent);
        }
    };
//    private Runnable activityToIndex2 =new  Runnable(){
//        @Override
//        public void run() {
//           App.toast.ToastMessageShort(String.valueOf());
//        }
//    };
}