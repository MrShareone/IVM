package com.ivmai.vidget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.ivmai.kehu.R;

public class AdDialog extends Dialog implements View.OnClickListener {
    private Context context;

    public AdDialog(Context context) {
        // 更改样式,把背景设置为透明的
        super(context, R.style.LocatonDialogStyle);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //加载dialog的布局
        setContentView(R.layout.view_location_dialog_dong);
        //拿到布局控件进行处理
        ImageView imageView = (ImageView) findViewById(R.id.mm);
        imageView.setOnClickListener(this);
        //初始化布局的位置
        initLayoutParams();
    }

    // 初始化布局的参数
    private void initLayoutParams() {
        // 布局的参数
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER;
        params.alpha = 1f;
        getWindow().setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}