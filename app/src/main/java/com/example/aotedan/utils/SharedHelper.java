package com.example.aotedan.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
// 保存数据工具类
public class SharedHelper {
    private Context mContext;

    public SharedHelper() {
    }

    public SharedHelper(Context mContext) {
        this.mContext = mContext;
    }

    //定义一个保存数据的方法
    public void save(String user_name, String user_phone,String token) {
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("user_name", user_name);
        editor.putString("user_phone", user_phone);
        editor.putString("token", token);
        editor.commit();
    }

    //定义一个读取SP文件的方法
    public Map<String, String> read() {
        Map<String, String> data = new HashMap<String, String>();
        SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        data.put("user_name", sp.getString("user_name", ""));
        data.put("user_phone", sp.getString("user_phone", ""));
        data.put("token",sp.getString("token",""));
        return data;
    }
}
