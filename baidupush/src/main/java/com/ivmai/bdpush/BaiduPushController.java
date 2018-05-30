package com.ivmai.bdpush;

import android.app.Notification;
import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;

import com.baidu.android.pushservice.BasicPushNotificationBuilder;
import com.baidu.android.pushservice.CustomPushNotificationBuilder;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushNotificationBuilder;
import com.baidu.android.pushservice.PushSettings;
import com.ivmai.bdpush.mr_shareone.baidupushlocaldemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR-SHAREONE on 2018/1/21.
 * 这是用于控制百度推送的类，把本就简单易用的bdpush再封装了一层，使其更接近自身应用需求
 * 如果想直接使用baidu的接口，依然也是可以的
 */

public class BaiduPushController{
//    全局上下文
    Context context;
    //    自定义通知状态栏构建类构造函数(定制通知栏基础样式)
    BasicPushNotificationBuilder basicPushNotificationBuilder;
    //    自定义通知状态栏构建类构造函数(定制通知栏基础样式及layout)。
    CustomPushNotificationBuilder customPushNotificationBuilder;
    /**
     * @param context 上下文
     */
    public BaiduPushController(Context context) {
        this.context = context;
        basicPushNotificationBuilder = new BasicPushNotificationBuilder();
        //以下代码就是设置了自定义的通知样式
        customPushNotificationBuilder =new CustomPushNotificationBuilder(
                R.layout.notification_custom_builder,
                R.id.notification_icon,
                R.id.notification_title,
                R.id.notification_text);
        customPushNotificationBuilder.setNotificationFlags(Notification.BADGE_ICON_SMALL);
        customPushNotificationBuilder.setNotificationDefaults(Notification.COLOR_DEFAULT);
        customPushNotificationBuilder.setStatusbarIcon(context.getApplicationInfo().icon);
        customPushNotificationBuilder.setNotificationSound(Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "6").toString());
        customPushNotificationBuilder.setChannelId("testId");
        customPushNotificationBuilder.setChannelName("testName");
        PushManager.setNotificationBuilder(context, 1, customPushNotificationBuilder);
    }

    public void start(){
        PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY, "niqHRcg2oNfKID0SNkNoPLFf");  //后端需要两个识别码，前端只需要一个  niqHRcg2oNfKID0SNkNoPLFf   VEgMu2lbsmaow1qmwscCDrSMFoHEDyoG
    }
    public void stop(){
        if(check()){
            PushManager.stopWork(context);
        }
    }
    public boolean check(){
        return  PushManager.isPushEnabled(context);
    }

    public boolean setTags(String userTag){
        List<String> user = new ArrayList<>();
        if(userTag != null){
            user.add(userTag);
        }
        if(user != null && user.size() != 0){
            PushManager.setTags(context,user);
            return true;
        }else{
            return false;
        }
    }

}
