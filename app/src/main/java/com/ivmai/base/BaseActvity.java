package com.ivmai.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.ivmai.kehu.R;
import com.ivmai.vidget.AdDialog;
import com.ivmai.vidget.MyProgressDialog;

import java.util.HashMap;

import butterknife.ButterKnife;
import okhttp3.MediaType;

/**
 * Created by MR-SHAREONE on 2018/1/18.
 * 这个类用于存放基类操作
 * 浸入式，保持竖屏
 */

public abstract class BaseActvity extends AppCompatActivity implements Handler.Callback {
    protected static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected String TAG;  //用于输出日志，初始化为继承的函数的名称
    protected Handler handler;  //继承的子类的网络访问都会用这个handler回调
    protected Context context;
    public Typeface typeFace;
    public HashMap<String, Object> hashMap = new HashMap<>();  //网络访问的时候存参数用的

    public MyProgressDialog progressDialog;  //网络进度条
    public AdDialog adDialog; //广告弹窗

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //保持竖屏
        setContentView(getViewId());
        setSystemTintBar();   //沉浸式
        ButterKnife.bind(this);
        handler = new Handler(this);
        context = getApplicationContext();
        TAG = this.getClass().getName();
        bindView();
        prepareWork();
    }

    /**
     * 将样式设置为透明状态栏
     */
    public void setSystemTintBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean handleMessage(Message message) {
        dissmissDialog();
        return false;
    }

    /**
     * 隐藏进度条
     */
    protected void dissmissDialog() {
        if (progressDialog == null)
            return;
        if (progressDialog.isShowing()) {
            try {

                progressDialog.dismiss();

            } catch (final IllegalArgumentException e) {
                // Handle or log or ignore
            } catch (final Exception e) {
                // Handle or log or ignore
            } finally {
                progressDialog = null;
            }
        }
    }


    /**
     * @param context 显示进度条
     * @param s       显示的消息
     */
    protected void showDialog(Context context, String s) {
        if (progressDialog == null) {
            progressDialog = new MyProgressDialog(context, R.style.ThemeOverlay_AppCompat_Dialog);
            if (!TextUtils.isEmpty(s)) {
                progressDialog.setMessage(s);
            } else {
                progressDialog.setMessage("正在加载…");
            }
            progressDialog.show();
        } else if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    /**
     * @param context 显示进度条
     */
    protected void showDialog(Context context) {
        if (progressDialog == null) {
            progressDialog = new MyProgressDialog(context, R.style.ThemeOverlay_AppCompat_Dialog);

            progressDialog.setMessage("正在加载…");

            progressDialog.show();
        } else if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    /**
     * @return
     * 获取主界面的id
     */
    public abstract int getViewId();

    /**
     * 准备工作，可能是一些数据的准备
     */
    public abstract void prepareWork();

    /**
     * 从主界面初始化组件
     */
    public abstract void bindView();
}
