package com.example.aotedan.ui.center;

import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.aotedan.R;
import com.example.aotedan.ui.gas.GasViewModel;

public class CenterFragment extends Fragment implements View.OnClickListener{
    private GasViewModel gasViewModel;
    public EditText send_text; //输入框
    public Button send_text_btn;// 发送按钮
    public Button get_btn;// get请求
    public Button record_btn;// 录音按钮
    public Button play_btn;// 播放按钮
    private boolean isStart = false; //开始录音标志
    private MediaRecorder mr = null; //mediaRecorder对象
    private Context mContext; //上下文
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        gasViewModel = ViewModelProviders.of(this).get(GasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_center, container, false);
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {

    }
}
