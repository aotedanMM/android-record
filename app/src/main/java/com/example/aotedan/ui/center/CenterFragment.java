package com.example.aotedan.ui.center;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aotedan.App.App;
import com.example.aotedan.R;
import com.example.aotedan.ui.gas.GasViewModel;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CenterFragment extends Fragment implements View.OnClickListener{
    private GasViewModel gasViewModel;
    private View center;
    private EditText send_text; //输入框
    private Button send_text_btn;// 发送按钮
    private Button record_btn;// 录音按钮
    private Button play_btn;// 播放按钮
    private Button send_voice_btn; // 发送按钮
    private boolean isStart = false; //开始录音标志
    private MediaRecorder mr = null; //mediaRecorder对象
    private Context mContext; //上下文
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gasViewModel = ViewModelProviders.of(this).get(GasViewModel.class);
        center = inflater.inflate(R.layout.fragment_center, container, false);
        return center;
    }
    public void onStart() {
        super.onStart();
        initView();
    }
    // 初始化控件
    private void initView() {
        send_text = center.findViewById(R.id.send_text);
        send_text_btn = center.findViewById(R.id.send_text_btn);
        record_btn = center.findViewById(R.id.record_btn);
        play_btn = center.findViewById(R.id.play_btn);
        send_voice_btn = center.findViewById(R.id.send_voice_btn);
        setView();
    }
    private void setView() {
        send_text_btn.setOnClickListener(this);
        record_btn.setOnClickListener(this);
        play_btn.setOnClickListener(this);
        send_voice_btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_text_btn:
                sendText();
                break;
            case R.id.record_btn:
                startRecord();
                break;
            case R.id.play_btn:
                playRecord();
                break;
            case R.id.send_voice_btn:
                sendVoice();
                break;
        }
    }
    // 发送文字
    private void sendText(){
        String url = "http://192.168.1.50:8080/sendText";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .header("Authorization","13243210010")
                .url(url)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("failure","onFailure");
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String resp = response.body().string();
                Log.i("resp",resp);
//                JSONObject respData = new JSONObject(resp);
//                int code = respData.optInt("code");
//                String msg = respData.getString("msg");
//                if(code == 200) {
//                    App.toast.ToastMessageShort(msg);
//                }
            }
        });
    }
    // 开始录音
    private void startRecord() {}
    // 播放录音
    private void playRecord() {}
    // 发送录音
    private void sendVoice() {}
}
