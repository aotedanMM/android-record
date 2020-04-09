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
import android.widget.Toast;

import com.example.aotedan.App.App;
import com.example.aotedan.MainActivity;
import com.example.aotedan.R;
import com.example.aotedan.utils.SSLSocketClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends Activity implements View.OnClickListener {
    private TextView title_bar;
    private EditText user_account;
    private EditText user_psd;
    private Button login_btn;
    private Button register;
    private Button forget;
    private String name;
    private String psd;

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
                Log.i("onClick", "register");
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
        Log.i("name", name);
        if (name.equals("")) {
            App.toast.ToastMessageShort("请输入账号");
            return;
        }
        if (psd.equals("")) {
            App.toast.ToastMessageShort("请输入密码");
            return;
        }
        App.toast.ToastMessageLong(name);
        App.toast.ToastMessageLong(psd);
        new Thread(new Runnable() {//在这个方法中同样还是先开启了一个子线程
            @Override
            public void run() {
                try {
                    String http_url = "http://192.168.1.50:8080/v1/api/wx/login";
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("wxUserAccount", "13243210010")
                            .add("wxUserPassword", "123456aaa")
                            .build();
                    Request request = new Request.Builder()
                            .url(http_url)
                            .post(requestBody)
                            .build();
//                    HTTPSCerUtils.setTrustAllCertificate(request);
                    Response response = client.newCall(request).execute();//接收服务器返回的数据
                    String responseData = response.body().string();//得到具体数据
                    Log.i("resp", responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    String msg = jsonObject.getString("msg");
                    int code = jsonObject.optInt("code");
                    if (code == 200) {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), "成功", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
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