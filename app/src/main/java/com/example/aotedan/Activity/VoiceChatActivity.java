package com.example.aotedan.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aotedan.Activity.LoginActivity;
import com.example.aotedan.App.App;
import com.example.aotedan.IndexActivity;
import com.example.aotedan.R;
import com.example.aotedan.bean.BaseRequestDataBean;
import com.example.aotedan.ui.gas.GasViewModel;
import com.example.aotedan.utils.SharedHelper;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VoiceChatActivity extends Activity implements View.OnClickListener{
    private View center;
    private EditText send_text; //输入框
    private Button send_text_btn;// 发送按钮
    private Button record_btn;// 录音按钮
    private Button play_btn;// 播放按钮
    private String fileUrl; //录音文件
    private Button send_voice_btn; // 发送按钮
    private boolean isStart = false; //开始录音标志
    private MediaRecorder mr = null; //mediaRecorder对象
    private BaseRequestDataBean baseRequestDataBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_chat);
        initView();
    }
    // 初始化控件
    private void initView() {
        send_text = findViewById(R.id.send_text);
        send_text_btn = findViewById(R.id.send_text_btn);
        record_btn = findViewById(R.id.record_btn);
        play_btn = findViewById(R.id.play_btn);
        send_voice_btn = findViewById(R.id.send_voice_btn);
        setView();
    }
    private void setView() {
        TextView title_bar = findViewById(R.id.title_bar);
        title_bar.setText("个人中心");
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
                record();
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
        String url = "http://192.168.1.50:8781/wx/sendText";
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("text", String.valueOf(send_text))
                .build();
        Request request = new Request.Builder()
                .header("Authorization","13243210010")
                .post(requestBody)
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
                Gson gson = new Gson();
                baseRequestDataBean = gson.fromJson(resp,BaseRequestDataBean.class);
                if(baseRequestDataBean.getCode()==200){
                    App.toast.ToastMessageShort(baseRequestDataBean.getMsg());
                } else {
                    App.toast.ToastMessageShort(baseRequestDataBean.getMsg());
                }

            }
        });
    }
    // 录音状态判断
    private void record() {
        if(!isStart){
            startRecord();
            record_btn.setText("停止");
            isStart = true;
        } else {
            stopRecord();
            record_btn.setText("录音");
            isStart = false;
        }
    }
    // 开始录音
    private void startRecord() {
        if(mr == null){
            File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),"sounds");
            Log.i("dir",String.valueOf(dir));
            if(!dir.exists()){
                dir.mkdirs();
            }
            File soundFile = new File(dir,System.currentTimeMillis()+".amr");
            fileUrl = String.valueOf(soundFile);
            Log.i("soundFile",String.valueOf(soundFile));
            if(!soundFile.exists()){
                try {
                    soundFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            mr = new MediaRecorder();
            mr.setAudioSource(MediaRecorder.AudioSource.MIC);  //音频输入源
            mr.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);   //设置输出格式
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);   //设置编码格式
            mr.setOutputFile(soundFile.getAbsolutePath());
            try {
                mr.prepare();
                mr.start();  //开始录制
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    // 结束录音
    private void stopRecord() {
        if(mr != null){
            mr.stop();
            mr.release();
            mr = null;
        }
    }
    // 播放录音
    private void playRecord() {
        MediaPlayer mPlayer = new MediaPlayer();
        try{
            Log.i("fileUrl",String.valueOf(fileUrl));
            mPlayer.setDataSource(fileUrl);
            mPlayer.prepare();
            mPlayer.start();
        }catch(IOException e){
            Log.e("playBtn","播放失败");
        }
    }
    // 发送录音
    private void sendVoice() {

    }
}
