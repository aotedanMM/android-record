package com.example.aotedan.Activity;
import android.app.Activity;
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
import com.example.aotedan.MainActivity;
import com.example.aotedan.R;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private TextView title_bar;
    private EditText register_account;
    private EditText register_psd;
    private EditText register_psd_again;
    private Button register_btn;
    private String account;
    private String psd;
    private String psd_again;
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
    private void register() {
        account = register_account.getText().toString().trim();
        psd = register_psd.getText().toString().trim();
        psd_again = register_psd_again.getText().toString().trim();
        if (account.equals("")) {
            App.toast.ToastMessageShort("请输入手机号");
            return;
        }
        if (psd.equals("")) {
            App.toast.ToastMessageShort("请输入密码");
            return;
        }
        if(psd_again.equals("")){
            App.toast.ToastMessageShort("请再次输入密码");
            return;
        } else if(!psd.equals(psd_again)){
            App.toast.ToastMessageShort("两次输入的密码不一致");
            return;
        }
        new Thread(new Runnable() {//在这个方法中同样还是先开启了一个子线程
            @Override
            public void run() {
                try {
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    JSONObject json = new JSONObject();
                    json.put("wxUserAccount",account);
                    json.put("wxUserPassword",psd);
                    String http_url = "http://192.168.1.50:8080/v1/api/wx/register";
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
                    Request request = new Request.Builder()
                            .header("Authorization","13243210010")
                            .url(http_url)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();//接收服务器返回的数据
                    String responseData = response.body().string();//得到具体数据
                    Log.i("resp",responseData);
                    JSONObject jsonObject = new JSONObject(responseData); // json转jsonObject
                    int code = jsonObject.optInt("code");// code
                    String msg = jsonObject.getString("msg");// msg
                    if(code == 200) {
                        Looper.prepare();
                        App.toast.ToastMessageShort(msg);
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        App.toast.ToastMessageShort(msg);
                        Looper.loop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
