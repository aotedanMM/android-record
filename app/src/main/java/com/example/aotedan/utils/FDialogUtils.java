package com.example.aotedan.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.aotedan.R;


/**
 * 自定义弹框
 * 
 * @author Administrator
 * 
 */
public class FDialogUtils {
	private Context context;
	private ImageView img_view;
	private Dialog dialog;
	private AnimationDrawable animationDrawable;
	public FDialogUtils(Context context) {
		this.context = context;
		diaLog();
	}

	private void diaLog() {

		LayoutInflater inflater = LayoutInflater.from(context);
		View layout = inflater.inflate(R.layout.fenghuolun, null);
		img_view = (ImageView) layout.findViewById(R.id.img_view);
//		dialog = new AlertDialog.Builder(context).create();
		dialog =new Dialog(context,R.style.dialog);
		dialog.setCancelable(false);

//		img_view.setBackgroundResource(R.drawable.frame_anim);

//		dialog.getWindow().setBackgroundDrawableResource(R.color.tou);

		dialog.show();
		//自动适应屏幕宽度修改弹框大小
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = lp.MATCH_PARENT;// 设置宽度
		lp.height = lp.MATCH_PARENT;// 设置宽度

		dialog.getWindow().setAttributes(lp);
		dialog.getWindow().setContentView(layout);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.e("tingzhi","11211");
            }
        });
animationDrawable = (AnimationDrawable) img_view.getBackground();
//		animationDrawable.start();
	}


	public void dimss() {
//		animationDrawable.stop();
		dialog.dismiss();
	}

}
