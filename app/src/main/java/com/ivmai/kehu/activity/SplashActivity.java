package com.ivmai.kehu.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.ivmai.base.BaseActvity;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;

public class SplashActivity extends BaseActvity {
    @Override
    public int getViewId() {
        return R.layout.activity_splash;
    }

    @Override
    public void prepareWork() {
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
//        registerReceiver(IvmApplication.netWorkStateReceiver, filter);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }
        },2000);
    }

    @Override
    public void bindView(){
    }
}
