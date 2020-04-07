package com.example.aotedan;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import org.json.JSONObject;
import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class MainActivity extends Activity {
    public Editable inputVal; //输入框的值
    public TextView tv_title;
    public BootstrapEditText send_text; //发送输入框
    public BootstrapButton send_text_btn;// 发送按钮
    public BootstrapButton get_btn;// get请求
    public String http_url; // http_url
    private BootstrapButton play_btn; //播放按钮
    private BootstrapButton record_btn; // 录音按钮
    private boolean isStart = false; //开始录音标志
    private MediaRecorder mr = null; //mediaRecorder对象
    private Context mContext; //上下文
    //语音文件保存路径
    private String fileUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_title = (TextView)findViewById(R.id.tv_title);
        tv_title.setGravity(Gravity.CENTER);
        mContext = getApplicationContext();
        play_btn = (BootstrapButton) findViewById(R.id.play_btn);
        send_text_btn = (BootstrapButton) findViewById(R.id.send_text_btn);
        get_btn = (BootstrapButton) findViewById(R.id.get_btn);
        send_text = (BootstrapEditText) findViewById(R.id.send_text);
        record_btn = (BootstrapButton) findViewById(R.id.record_btn);
        send_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("input_value", String.valueOf(s));
                inputVal = s;
            }
        });
        send_text_btn.setOnClickListener(new SendTextBtnListener()); //发送按钮
        get_btn.setOnClickListener(new GetBtnListener()); // get请求按钮
        record_btn.setOnClickListener(new RecordBtnListener());// 录音按钮
        play_btn.setOnClickListener(new PlayRecordListener()); //播放按钮
    }
    //发送按钮
    class SendTextBtnListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Log.i("input_value", String.valueOf(inputVal));
            postSendText();
        }
    }
    //播放按钮
    class PlayRecordListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
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
    }
    // get请求按钮
    class GetBtnListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            getSendText();
        }
    }
//    录音按钮事件
    class RecordBtnListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(!isStart){
                startRecord();
                record_btn.setText("停止");
                isStart = true;
            }else{
                stopRecord();
                record_btn.setText("录音");
                isStart = false;
            }
        }
    }
    // post请求
    private void postSendText() {
        new Thread(new Runnable() {//在这个方法中同样还是先开启了一个子线程
            @Override
            public void run() {
                try {
                    http_url = "http://192.168.1.50:8781/wx/sendText";
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("text", String.valueOf(inputVal))
                            .build();
                    Request request = new Request.Builder()
                            .url(http_url)
                            .post(requestBody)
                            .build();  //然后在子线程里使用OkHttp发出一条HTTP请求，请求的目标地址还是百度的首页的一样。
                    Response response = client.newCall(request).execute();//接收服务器返回的数据
                    String responseData = response.body().string();//得到具体数据
                    Log.i("resp",responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    String msg = jsonObject.getString("msg");
                    int code = jsonObject.optInt("code");
                    if(code == 200) {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(),"成功", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    // get请求
    private void getSendText(){
        new Thread(new Runnable() {//在这个方法中同样还是先开启了一个子线程
            @Override
            public void run() {
                try {
                    http_url = "http://192.168.1.50:8781/wx/receiveText";
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url(http_url)
                            .build();  //然后在子线程里使用OkHttp发出一条HTTP请求，请求的目标地址还是百度的首页的一样。
                    Response response = client.newCall(request).execute();//接收服务器返回的数据
                    String responseData = response.body().string();//得到具体数据
                    Log.i("resp",responseData);
                    JSONObject jsonObject = new JSONObject(responseData);
                    String resp_data = jsonObject.getString("data");
                    String msg = jsonObject.getString("msg");
                    int code = jsonObject.optInt("code");
                    Log.i("data",resp_data);
                    if(code == 200) {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {
                        Looper.prepare();
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //开始录制
    private void startRecord(){
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
    //停止录制，资源释放
    private void stopRecord(){
        if(mr != null){
            mr.stop();
            mr.release();
            mr = null;
        }
    }
}