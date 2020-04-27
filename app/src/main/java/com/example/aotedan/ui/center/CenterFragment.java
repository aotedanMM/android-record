package com.example.aotedan.ui.center;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.aotedan.Activity.LoginActivity;
import com.example.aotedan.Activity.VoiceChatActivity;
import com.example.aotedan.Activity.WelcomeActivity;
import com.example.aotedan.App.App;
import com.example.aotedan.R;
import com.example.aotedan.bean.BaseRequestDataBean;
import com.example.aotedan.utils.SharedHelper;
import com.google.gson.Gson;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CenterFragment extends Fragment implements View.OnClickListener{
    private View center;
    private SharedHelper sh;
    private Button logout_btn;
    private TextView center_user_name;
    private TextView center_user_phone;
    private View voice_chat;
    private View history_record;
    private View about_us;
    private Context mContext; //上下文
    private BaseRequestDataBean baseRequestDataBean;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        center = inflater.inflate(R.layout.fragment_center, container, false);
        return center;
    }
    public void onStart() {
        super.onStart();
        initView();
    }
    // 初始化控件
    private void initView() {
        mContext = getActivity().getApplicationContext();
        sh = new SharedHelper(mContext);
        logout_btn = center.findViewById(R.id.logout_btn);
        center_user_name = center.findViewById(R.id.center_user_name);
        center_user_phone = center.findViewById(R.id.center_user_phone);
        voice_chat = center.findViewById(R.id.voice_chat);
        history_record = center.findViewById(R.id.history_record);
        about_us = center.findViewById(R.id.about_us);
        setView();
    }
    private void setView() {
        TextView title_bar = center.findViewById(R.id.title_bar);
        title_bar.setText("个人中心");
        logout_btn.setOnClickListener(this);
        Map<String,String> data = sh.read();
        center_user_name.setText(data.get("user_name"));
        center_user_phone.setText(data.get("user_phone"));
        voice_chat.setOnClickListener(this);
        history_record.setOnClickListener(this);
        about_us.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout_btn:
                logout();
                break;
            case R.id.voice_chat:
                activityToVoiceChat();
                break;
            case R.id.history_record:
                activityToHistoryRecord();
                break;
            case R.id.about_us:
                activityToAboutUs();
                break;
        }
    }
    // 跳转到语音通话
    private void activityToVoiceChat() {
        Intent intent = new Intent(getActivity(), VoiceChatActivity.class);
        startActivity(intent);
    }
    // 跳转到历史超标
    private void activityToHistoryRecord() {
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        startActivity(intent);
    }
    // 跳转到关于我们
    private  void activityToAboutUs() {
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        startActivity(intent);
    }
    // 退出登录
    private void logout() {
        // get请求带参数
        Request.Builder reqBuild = new Request.Builder();
        HttpUrl.Builder urlBuilder =HttpUrl.parse("http://192.168.1.50:8080/v1/api/wx/logout")
                .newBuilder();
        urlBuilder.addQueryParameter("key","13243210010");
        reqBuild.url(urlBuilder.build());
        Request request = reqBuild.build();

        OkHttpClient okHttpClient = new OkHttpClient();
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
//                    App.toast.ToastMessageShort(baseRequestDataBean.getMsg());
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    App.toast.ToastMessageShort(baseRequestDataBean.getMsg());
                }
            }
        });
    }
}
