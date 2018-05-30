package com.ivmai.kehu.broadcastrec;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.ivmai.base.IvmApplication;

/**
 * Created by Carson_Ho on 16/10/31.
 * 网络状态发生改变的时候
 */
public class NetWorkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectionManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
//            switch (networkInfo.getType()) {
//                case ConnectivityManager.TYPE_MOBILE:
//                    Toast.makeText(context, "正在使用2G/3G/4G网络", Toast.LENGTH_SHORT).show();
//                    break;
//                case ConnectivityManager.TYPE_WIFI:
//                    Toast.makeText(context, "正在使用wifi上网", Toast.LENGTH_SHORT).show();
//                    break;
//                default:
//                    break;
//            }
        IvmApplication.isNetValuable = true;
        } else {
//            Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
            IvmApplication.isNetValuable = false;

        }
    }
}
