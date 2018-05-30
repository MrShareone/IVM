package com.ivmai.base;

import android.content.Context;

import com.ivm.entity.TKehu;
import com.ivm.newentity.KehuNoteBean;
import com.ivmai.bdpush.BaiduPushController;
import com.ivmai.kehu.broadcastrec.NetWorkStateReceiver;
import com.ivmai.utils.Config;
import com.ivmai.utils.IP;
import com.ivmai.utils.NetWorkStateManager;
import com.ta.utdid2.android.utils.PhoneInfoUtils;

/**
 * Created by MR-SHAREONE on 2017/8/6.
 */
public class IvmApplication extends android.app.Application {
    public static String TAG = "IvmApplication";

    public static Config config;   //管理sharepreference，存一些应用配置信息

    public static boolean islogin = false;

    public static TKehu kehu;   //登录后返回的信息就放在这里，有用户的信息，可以直接取

    public static Context context;

    public static boolean isNetValuable = true;   //default true

    public static KehuNoteBean kehuNoteBean;

    public static BaiduPushController controller;   //for bdpush

    public static NetWorkStateReceiver netWorkStateReceiver;        //网络状态变化接收器

    public static String phoneImei = "";   //手机imei

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        IP.initIP(IP.ONLINE);
        config = new Config(context);
        context = getApplicationContext();
        //注册网络监听
        IvmApplication.netWorkStateReceiver = new NetWorkStateReceiver();
//        //init netstate
        NetWorkStateManager.refreshState(context);
        //这里启动百度监听器
        controller = new BaiduPushController(getApplicationContext());

        phoneImei = PhoneInfoUtils.getImei(context);
    }
}

