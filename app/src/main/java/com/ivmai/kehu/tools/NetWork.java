package com.ivmai.kehu.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ivmai.utils.LogUtil;

/**
 * Created by lk on 2017/8/23.
 * 用于判断是否有网络
 */

public class NetWork {

    //判断有没有联网
    public static boolean isNetwork(Context cxt) {
        NetworkInfo networkInfo = null;
        try {
            ConnectivityManager cm = (ConnectivityManager) cxt.getSystemService(Context.CONNECTIVITY_SERVICE);
            networkInfo = cm.getActiveNetworkInfo();
        } catch (Exception e) {
            LogUtil.log("network","权限不足无法访问网络");
        }
        if (networkInfo == null) {
            return false;
        } else {
            return true;
        }
    }

}
