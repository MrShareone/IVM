package com.ivmai.vidget;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ivmai.kehu.R;


public class MyProgressDialog extends Dialog {

	private TextView tvMsg;

	public MyProgressDialog(Context context) {
        super(context);
		init();
	}

	public MyProgressDialog(Context context, int theme) {
		super(context, theme);
		init();
	}

	private void init() {
		View v = View.inflate(getContext(), R.layout.myprogressdialog, null);
		tvMsg = (TextView) v.findViewById(R.id.tv_loadingmsg);
		ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.pb);

		if (Build.VERSION.SDK_INT > 22) {//android 6.0替换clip的加载动画
//			Drawable drawable = getContext().getApplicationContext().getResources().getDrawable(R.drawable.roate_bg_black_anim_list_6);
//			progressBar.setIndeterminateDrawable(drawable);
		}
		setContentView(v);
		setCancelable(true);
		setCanceledOnTouchOutside(false);

		getWindow().getAttributes().gravity = Gravity.CENTER;
	}

	public void setMessage(String msg) {
		if(tvMsg==null){
			return;
		}
		if (msg != null) {
			tvMsg.setText(msg);
			tvMsg.setVisibility(View.VISIBLE);
		} else {
			tvMsg.setVisibility(View.GONE);
		}
	}
}