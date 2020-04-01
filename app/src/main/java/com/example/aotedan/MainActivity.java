package com.example.aotedan;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public Button send_text_btn;
    public EditText send_text;
    public Editable inputVal;
    public String http_url;
    public Button get_btn;
    private Button record_btn;
    private boolean isStart = false;
    private MediaRecorder mr = null;
    private Button play_btn;
    private EditText editname;
    private EditText editdetail;
    private Button btnsave;
    private Button btnclean;
    private Button btnread;
    private Context mContext;
    //语音文件保存路径
    private String fileUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        bindViews();
        play_btn = (Button) findViewById(R.id.play_btn);
        send_text_btn = (Button) findViewById(R.id.send_text_btn);
        get_btn = (Button) findViewById(R.id.get_btn);
        send_text = (EditText) findViewById(R.id.send_text);
        record_btn = (Button) findViewById(R.id.record_btn);
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
        get_btn.setOnClickListener(new GetBtnListener()); // 测试提示信息按钮
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
    // 测试提示信息按钮
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
                record_btn.setText("停止录制");
                isStart = true;
            }else{
                stopRecord();
                record_btn.setText("开始录制");
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
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
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
                    String msg = jsonObject.getString("msg");
                    String resp_data = jsonObject.getString("data");
                    Log.i("data",resp_data);
                    Log.i("msg",msg);
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

    private void bindViews() {
        editname = (EditText) findViewById(R.id.edittitle);
        editdetail = (EditText) findViewById(R.id.editdetail);
        btnsave = (Button) findViewById(R.id.btnsave);
        btnclean = (Button) findViewById(R.id.btnclean);
        btnread = (Button) findViewById(R.id.btnread);

        btnsave.setOnClickListener(this);
        btnclean.setOnClickListener(this);
        btnread.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnclean:
                editdetail.setText("");
                editname.setText("");
                break;
            case R.id.btnsave:
                String filename = editname.getText().toString();
                String filedetail = editdetail.getText().toString();
                SDFileHelper sdHelper = new SDFileHelper(mContext);
                try
                {
                    sdHelper.savaFileToSD(filename, filedetail);
                    Toast.makeText(getApplicationContext(), "数据写入成功", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "数据写入失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnread:
                String detail = "";
                SDFileHelper sdHelper2 = new SDFileHelper(mContext);
                try
                {
                    String filename2 = editname.getText().toString();
                    detail = sdHelper2.readFromSD(filename2);
                }
                catch(IOException e){e.printStackTrace();}
                Toast.makeText(getApplicationContext(), detail, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}