package com.example.aotedan.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aotedan.R;

public class AboutUsActivity extends Activity implements View.OnClickListener{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();
        setTitle();
    }
    private void initView() {}
    private void setTitle() {
        TextView title_bar = (TextView)findViewById(R.id.title_bar);
        title_bar.setText("关于我们");
        ImageView title_back = (ImageView) findViewById(R.id.title_back);
        title_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_back:
                AboutUsActivity.this.finish();
                break;

        }

    }
}
