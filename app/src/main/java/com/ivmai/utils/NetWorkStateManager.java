package com.ivmai.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.ivmai.base.IvmApplication;

/**
 * 网络状态管理，主要功能就是初始化和刷新存放于application内的网络状态
 * Created by MR-SHAREONE on 2018/2/4.
 */

public class NetWorkStateManager {

    /**
     * @param context 刷新功能
     */
    public static void refreshState(Context context) {
//        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
//        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
//            checkState_21(context);
//            //API大于23时使用下面的方式进行网络监听
//        } else {
//            checkState_21orNew(context);
//        }
//    }

    //检测当前的网络状态
    //API版本23以下时调用此方法进行检测
    //因为API23后getNetworkInfo(int networkType)方法被弃用
//    private static void checkState_21(Context context) {
        //步骤1：通过Context.getSystemService(Context.CONNECTIVITY_SERVICE)获得ConnectivityManager对象
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        //步骤2：获取ConnectivityManager对象对应的NetworkInfo对象
        //NetworkInfo对象包含网络连接的所有信息
        //步骤3：根据需要取出网络连接信息

        NetworkInfo networkInfo;

        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            IvmApplication.isNetValuable = true;
        } else {
            IvmApplication.isNetValuable = false;

        }
    }

    //API版本23及以上时调用此方法进行网络的检测
//步骤非常类似
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private static void checkState_21orNew(Context context) {
//        //获得ConnectivityManager对象
//        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        //获取所有网络连接的信息
//        Network[] networks = connMgr.getAllNetworks();
//        //用于存放网络连接信息
//        StringBuilder sb = new StringBuilder();
//
//        NetworkInfo wifi, mobile;
//
//        Boolean wifistate = false, mobilestate = false;
//
//        //通过循环将网络信息逐个取出来
//        for (int i = 0; i < networks.length; i++) {
//            //获取ConnectivityManager对象对应的NetworkInfo对象
//            NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
//            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//                mobile = networkInfo;
//                IvmApplication.isNetValuable = mobile.isConnected();
//            }else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
//                wifi = networkInfo;
//                IvmApplication.isNetValuable = wifi.isConnected();
//            }else{
//                IvmApplication.isNetValuable = false;
//            }
//        }
//    }
}
