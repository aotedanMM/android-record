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
import com.example.aotedan.R;

import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ForgetActivity extends Activity implements View.OnClickListener {
    private TextView title_bar;
    private EditText forget_account;
    private EditText forget_id_card;
    private EditText new_psd;
    private Button forget_btn;
    private String account;
    private String id_card;
    private String psd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);
        initView();
    }
    private void initView() {
        forget_account=(EditText) findViewById(R.id.forget_account);
        forget_id_card = (EditText)findViewById(R.id.id_card);
        new_psd = (EditText) findViewById(R.id.new_psd);
        forget_btn = (Button) findViewById(R.id.forget_btn);
        // 设置标题栏
        title_bar=findViewById(R.id.title_bar);
        title_bar.setText("忘记密码");
        setView();
    }
    private void setView() {
        forget_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forget_btn:
                forget_btn();
                break;
        }
    }
    //    找回密码
    private void forget_btn(){
        account = forget_account.getText().toString().trim();
        id_card = forget_id_card.getText().toString().trim();
        psd = new_psd.getText().toString().trim();
        if(account.equals("")){
            App.toast.ToastMessageShort("请输入手机号");
            return;
        }
        if(id_card.equals("")){
            App.toast.ToastMessageShort("请输入身份证号");
            return;
        }
        if(psd.equals("")){
            App.toast.ToastMessageShort("请输入新密码");
            return;
        }
        new Thread(new Runnable() {//在这个方法中同样还是先开启了一个子线程
            @Override
            public void run() {
                try {
                    MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                    JSONObject json = new JSONObject();
                    json.put("account",account);
                    json.put("idCard","136263199102162681");
                    json.put("newPassword",psd);
                    String http_url = "http://192.168.1.50:8080/v1/api/wx/forgetPassword";
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
                        Intent intent = new Intent(ForgetActivity.this, LoginActivity.class);
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
