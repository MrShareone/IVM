package com.ivmai.vidget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ivmai.kehu.R;

/**
 * Created by MR-SHAREONE on 2018/1/18.
 * 主界面导航栏底部tab
 */

public class BottomTab extends RelativeLayout {

    TextView textView;
    ImageView imageView;
    View view;
    View redDot;  //红点

    public BottomTab(Context context) {
        super(context);
        initView(context);
    }

    public BottomTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public BottomTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        view = View.inflate(context, R.layout.bottom_tab, BottomTab.this);
        imageView = findViewById(R.id.bottom_tab_img);
        textView = findViewById(R.id.bottom_tab_tv);
        redDot = findViewById(R.id.reddot);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if(selected){
            textView.setTextColor(Color.parseColor("#02a3e8"));
        }else{
            textView.setTextColor(Color.parseColor("#212121"));
        }
    }


    public void setTextView(String content){
        textView.setText(content);
    }

    public void setImageView(int resId){
        imageView.setImageResource(resId);
    }

    public void showDot(boolean b){
        if(b){
            redDot.setVisibility(VISIBLE);
        }else{
            redDot.setVisibility(INVISIBLE);
        }
    }
}
