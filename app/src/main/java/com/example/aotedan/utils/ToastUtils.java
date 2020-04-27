package com.example.aotedan.utils;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author Administrator
 */
public class ToastUtils {
    private Toast toast = null;
    private Context context;

    public ToastUtils(Context context) {
        this.context = context;
    }

    public void ToastMessageLong(String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

    public void ToastMessageShort(String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
        } else {
            toast.setText(content);
        }
        toast.show();
    }

}
