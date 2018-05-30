package com.ivmai.kehu.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.content.FileProvider;
import android.widget.Toast;

import com.ivmai.utils.IP;

import java.io.File;

public class UpdateService extends Service{
    public UpdateService() {
    }

    public static final String DOWNURL = IP.SERVERURL+"apk/ivmai.apk";
    BroadcastReceiver receiver;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        receiverRegist();
        downApk();
        return Service.START_STICKY;
    }

    public void receiverRegist() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                installApk(context);
                stopSelf();
            }
        };

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(receiver, filter);
    }

    public void installApk(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);

        File file=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "爱微迈");

        if (Build.VERSION.SDK_INT >= 24) {
            //provider authorities
            Uri apkUri = FileProvider.getUriForFile(context, "com.mydomain.fileprovider", file);
            //Granting Temporary Permissions to a URI
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
//从一个service中启动一个activity，则intent必须要添加FLAG_ACTIVITY_NEW_TASK标记
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    public void downApk() {

        File apkFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                 .getPath()+"/ivmai.apk");

        if(apkFile.exists()){
            Toast.makeText(this,"已经有apk存在，将要删除", Toast.LENGTH_LONG).show();
            apkFile.delete();
        }

        DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(DOWNURL));
        request.setMimeType("application/vnd.android.package-archive");
        //request.setDescription("XXXX");
        //request.setTitle("XXX");
        request.setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS, "ivmai.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        manager.enqueue(request);
    }
    @Override
    public void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();
    }
}
