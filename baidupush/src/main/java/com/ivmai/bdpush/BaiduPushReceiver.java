package com.ivmai.bdpush;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.baidu.android.pushservice.PushMessageReceiver;



import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by MR-SHAREONE on 2018/1/21.
 * 百度消息处理类，百度推送服务的所有回调在这里处理
 */

public class BaiduPushReceiver extends PushMessageReceiver{
    String TAG = "BaiduPushReceiver";

    @Override
    public void onBind(Context context, int i, String s, String s1, String s2, String s3) {

    }

    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    /**
     * PushMessageReceiver的抽象方法，把receiver类继承PushMessageReceiver可以使用。接收透传消息。
     * @param context 上下文
     * @param s 推送的消息
     * @param s1 自定义内容，为空或者json字符串
     */
    @Override
    public void onMessage(Context context, String s, String s1){
        EventBus.getDefault().post(new JumpEvent(s));
        EventBus.getDefault().post(new ChatMsgEvet());
    }

    /**
     * PushMessageReceiver的抽象方法，把receiver类继承PushMessageReceiver可以使用。接收通知点击的函数。
     * @param context 上下文
     * @param s 推送的通知的标题
     * @param s1 推送的通知的描述
     * @param s2  自定义内容，为空或者json字符串
     */
    @Override
    public void onNotificationClicked(Context context, String s, String s1, String s2) {
//        EventBus.getDefault().post(new NoticificationClickEvent());
    }
    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

        EventBus.getDefault().post(new CommonEvent(1,"adsf"));
        Log.e(TAG,"收到推送");
    }

}
