package com.ivmai.kehu.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import com.ivmai.base.BaseActvity;
import com.ivmai.kehu.R;
import com.ivmai.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
/**
*CommonWebview
*公共的网络浏览器
*MR-SHAREONE
*2018/5/13
*18:57
**/
public class CommonWebview extends BaseActvity {

    @BindView(R.id.common_webview)
    WebView commonWevView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String string = intent.getStringExtra("from");
        if(!TextUtils.isEmpty(string)){
            if(string.equals("aboutus")){
                commonWevView.loadUrl(("file:///android_asset/about_us.html"));
            }else if(string.equals("xieyi")){
                commonWevView.loadUrl(("file:///android_asset/xieyi.html"));
            }
        }
    }

    @Override
    public int getViewId() {
        return R.layout.activity_common_webview;
    }

    @Override
    public void prepareWork() {
        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    public void bindView() {

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
