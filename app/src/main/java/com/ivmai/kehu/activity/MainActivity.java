package com.ivmai.kehu.activity;

import android.content.Intent;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.client.android.CaptureActivity;
import com.ivmai.base.BaseActvity;
import com.ivmai.base.IvmApplication;
import com.ivmai.bdpush.ChatMsgEvet;
import com.ivmai.bdpush.CommonEvent;
import com.ivmai.bdpush.JumpEvent;
import com.ivmai.kehu.chat.activity.Chat;
import com.ivmai.kehu.chat.bean.Msg;
import com.ivmai.kehu.chat.bean.MsgList;
import com.ivmai.kehu.chat.bean.MsgListList;
import com.ivmai.kehu.fragment.MyVipCardFragment;
import com.ivmai.kehu.fragment.ScanOrderFragment;
import com.ivmai.kehu.fragment.SearchOrderFragment;
import com.ivmai.kehu.fragment.MySettingFragment;
import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.utils.GsonUtil;
import com.ivmai.utils.LogUtil;
import com.ivmai.vidget.AdDialog;
import com.ivmai.vidget.BottomTab;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends BaseActvity implements View.OnClickListener {
    FragmentManager fragmentManager;
    private boolean isExit = false;
    LinearLayout tabContent;

    private BottomTab scanTab, searchTab, myVipTab, mySettingTab,goScan;  //底部的四个tab
    private FrameLayout frameLayout;   //包含fragment的布局

    private ScanOrderFragment scanOrderFragment;
    private SearchOrderFragment searchOrderFragment;
    private MyVipCardFragment myVipCardFragment;
    private MySettingFragment mySettingFragment;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void bindView() {
        frameLayout = (FrameLayout) findViewById(R.id.fragment_container);
        tabContent = (LinearLayout) findViewById(R.id.tabs);
        scanTab = (BottomTab) findViewById(R.id.scan_order);
        scanTab.setImageView(R.drawable.order);
        scanTab.setTextView("当前订单");
        scanTab.setOnClickListener(this);
        searchTab = (BottomTab) findViewById(R.id.search_order);
        searchTab.setImageView(R.drawable.search);
        searchTab.setTextView("发现");
        searchTab.setOnClickListener(this);
        myVipTab = (BottomTab) findViewById(R.id.my_vip);
        myVipTab.setImageView(R.drawable.vipmenber);
        myVipTab.setTextView("我的会员");
        myVipTab.setOnClickListener(this);
        mySettingTab = (BottomTab) findViewById(R.id.my_setting);
        mySettingTab.setImageView(R.drawable.my);
        mySettingTab.setTextView("我的");
        mySettingTab.setOnClickListener(this);
        goScan = (BottomTab) findViewById(R.id.go_scan);
        goScan.setImageView(R.drawable.qrcode);
        goScan.setTextView("扫码下单");
    }

    @Override
    public int getViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void prepareWork() {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        scanOrderFragment = new ScanOrderFragment();
        transaction.add(R.id.fragment_container, scanOrderFragment);
        transaction.show(scanOrderFragment);
        transaction.commit();
        scanTab.setSelected(true);
        scanTab.setImageView(R.drawable.select_order);
        IvmApplication.controller.start(); //启动百度云推送
        setTag(); //用当前登录用户作为tag发送到百度云端
        EventBus.getDefault().register(this); // 注册订阅者


        goScan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /*跳转扫码界面*/
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                intent.putExtra("aa",100);
                ToastUtil.showToast(context,"正在进入扫码界面");
                startActivityForResult(intent,100);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!IvmApplication.controller.check()){
            IvmApplication.controller.start();
        }

    }

    //捕获用户按下back键，执行exit()方法
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            handler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }
    @Override
    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                isExit = false;
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 隐藏所有fragment单元
     */
    public void hideAllFragment(FragmentTransaction transaction) {
        if (scanOrderFragment != null) {
            transaction.hide(scanOrderFragment);
        }
        if (searchOrderFragment != null) {
            transaction.hide(searchOrderFragment);
        }
        if (mySettingFragment != null) {
            transaction.hide(mySettingFragment);
        }
        if (myVipCardFragment != null) {
            transaction.hide(myVipCardFragment);
        }
    }

    /*
    *把用户id设置为百度推送的唯一识别号
     */
    public void setTag(){
        if(IvmApplication.islogin){
            if(IvmApplication.kehu!= null){
                    if(IvmApplication.kehu.khTel != null){
                        //把电话号码作为tag发送出去
                    IvmApplication.controller.setTags(IvmApplication.kehu.khTel.toString());
                }else{
                    LogUtil.log(TAG,"未成功设置bdpush tag");
                }
            }else{
                LogUtil.log(TAG,"未成功设置bdpush tag");
            }
        }else{
            LogUtil.log(TAG,"未成功设置bdpush tag");
        }
    }


    @Override
    public void onClick(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        setAllTabUnselected();
        boolean istabclicked = false;   //这个变量判断是否点击到了tab,默认为false
        switch (view.getId()) {
            case R.id.scan_order:
                scanTab.showDot(false);
                if (scanOrderFragment == null)
                    scanOrderFragment = new ScanOrderFragment();
                if (!scanOrderFragment.isAdded())
                    transaction.add(R.id.fragment_container, scanOrderFragment);
                transaction.show(scanOrderFragment);
                scanTab.setSelected(true);
                scanTab.setImageView(R.drawable.select_order);
                istabclicked = true;
                break;
            case R.id.search_order:
                searchTab.showDot(false);
                if (searchOrderFragment == null)
                    searchOrderFragment = new SearchOrderFragment();
                if (!searchOrderFragment.isAdded())
                    transaction.add(R.id.fragment_container, searchOrderFragment);
                transaction.show(searchOrderFragment);
                searchTab.setSelected(true);
                searchTab.setImageView(R.drawable.select_search);
                istabclicked = true;
                break;
            case R.id.my_vip:
                myVipTab.showDot(false);
                if (myVipCardFragment == null)
                    myVipCardFragment = new MyVipCardFragment();
                if (!myVipCardFragment.isAdded())
                    transaction.add(R.id.fragment_container, myVipCardFragment);
                transaction.show(myVipCardFragment);
                myVipTab.setSelected(true);
                myVipTab.setImageView(R.drawable.select_vip);
                istabclicked = true;
                break;
            case R.id.my_setting:
                mySettingTab.showDot(false);
                if (mySettingFragment == null)
                    mySettingFragment = new MySettingFragment();
                if (!mySettingFragment.isAdded())
                    transaction.add(R.id.fragment_container, mySettingFragment);
                transaction.show(mySettingFragment);
                mySettingTab.setSelected(true);
                mySettingTab.setImageView(R.drawable.select_my);
                istabclicked = true;
                break;
            default:
                ;
        }
        if (istabclicked)
            transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == 2){
                String res = data.getStringExtra("data");
                if(!TextUtils.isEmpty(res)){
//                    ToastUtil.showToast(context,res);
                }

            }
        }
    }
    //收到透传广告
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(CommonEvent event) {
        if(adDialog != null){
            adDialog.show();
        }else{
            adDialog = new AdDialog(context);
            adDialog.show();
        }
    }
    //收到消息，这个位置有红点
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChatMsgEvet event){
        if(!mySettingFragment.isVisible()){
            if(event.a){
                mySettingTab.showDot(true);
            }else{
                mySettingTab.showDot(false);
            }
        }
    }
//
//    //收到消息，这个位置有红点
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(NoticificationClickEvent event){
//        startActivity(new Intent(context,OrderActivity.class));
//    }
    //点击notification跳转到聊天界面
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(JumpEvent event) {
        if(event.msgjson != null && !event.msgjson.equals("")){
            Msg msg = GsonUtil.fromJson(event.msgjson, Msg.class);

            MsgListList msgListList = IvmApplication.config.getChatMsg("");

            Boolean alreayIn = false;   //表示这个消息队列原来有没有

            if(msgListList != null){
                //找到对应的消息列表，存到其中,更改为新消息到来状态
                for(int i=0;i<msgListList.getMsgLists().size();i++){
                    if(msgListList.getMsgLists().get(i).clientID.equals(msg.sendID)){
                        msgListList.getMsgLists().get(i).msgs.add(msg);
                        msgListList.getMsgLists().get(i).newMessage =true;
                        IvmApplication.config.setChatmsg(msgListList);
                        alreayIn = true;
                    }
                }
            }else{
                msgListList = new MsgListList();
            }

            if(!alreayIn){

                //如果没有那个消息，就新建一个消息列表存入其中
                ArrayList<Msg> msgs = new ArrayList<>();
                msgs.add(msg);
                MsgList msgList = new MsgList("1",msg.sendID,msgs,true);
                msgListList.getMsgLists().add(msgList);
                IvmApplication.config.setChatmsg(msgListList);
            }

            EventBus.getDefault().post(new DataChangeEvent());  //通知当前界面更新
//
//            Intent intent = new Intent(getApplicationContext(), Chat.class);
//            intent.putExtra("frommainactivity",msg);
//            startActivity(intent);
        }
    }

    /**
    *DataChangeEvent
    *存储数据已经改变，通知联系人界面和聊天界面刷新
    *MR-SHAREONE
    *2018/5/15
    *23:46
    **/
    public class DataChangeEvent{
        public Boolean b = true;

        public DataChangeEvent() {
            b = true;
        }

        public DataChangeEvent(Boolean b) {
            this.b = b;
        }
    }



    /**
     * 让所有tab都不被选中
     */
    public void setAllTabUnselected() {
        scanTab.setSelected(false);
        scanTab.setImageView(R.drawable.order);
        searchTab.setSelected(false);
        searchTab.setImageView(R.drawable.search);
        myVipTab.setSelected(false);
        myVipTab.setImageView(R.drawable.vipmenber);
        mySettingTab.setSelected(false);
        mySettingTab.setImageView(R.drawable.my);
        goScan.setSelected(false);
        goScan.setImageView(R.drawable.qrcode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
