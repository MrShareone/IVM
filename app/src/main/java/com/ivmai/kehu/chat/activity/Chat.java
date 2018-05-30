package com.ivmai.kehu.chat.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivmai.base.BaseActvity;
import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;
import com.ivmai.kehu.activity.MainActivity;
import com.ivmai.kehu.chat.adapter.MsgAdapter;
import com.ivmai.kehu.chat.bean.Msg;
import com.ivmai.kehu.chat.bean.MsgList;
import com.ivmai.kehu.chat.bean.MsgListList;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.utils.GsonUtil;
import com.ivmai.utils.IP;
import com.ivmai.utils.LogUtil;
import com.ivmai.utils.NetWorkUtils;
import com.ivmai.utils.SoftKeyBoardListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Chat
 * 这是聊天界面
 * MR-SHAREONE
 * 2018/5/15
 * 8:59
 **/
public class Chat extends BaseActvity implements View.OnClickListener {

    ImageView backBotton;
    ImageView detailBtn;
    ImageView sendBtn;
    TextView textView;
    RecyclerView msgRecyclerView;
    EditText editBox;
    MsgList msgList;
    MsgAdapter msgAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        setLast();
    }
    /**
     * 返回的时候存入缓存。
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        msgList.newMessage = false;
        EventBus.getDefault().post(new EventInChat(msgList));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getViewId() {
        return R.layout.activity_chat;
    }

    @Override
    public void prepareWork() {
        Intent intent = getIntent();
        if (intent != null) {
            MsgList tmsgList = (MsgList) intent.getSerializableExtra("fromeContacts");   //从好友列表点击进入
            if (tmsgList != null) {
                msgList = tmsgList;
            } else {
                tmsgList = (MsgList) intent.getSerializableExtra("frommainactivity");
                if (tmsgList != null) {
                    msgList = tmsgList;
                }
            }
        }
        textView.setText(msgList.clientID); //设置聊天对象的名称
        sendBtn.setOnClickListener(this);
        detailBtn.setOnClickListener(this);
        backBotton.setOnClickListener(this);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        msgRecyclerView.setLayoutManager(linearLayoutManager);
        msgAdapter = new MsgAdapter(getApplicationContext(), msgList.msgs);
        msgRecyclerView.setAdapter(msgAdapter);

        //监听键盘，键盘弹出或者收起都要把聊天消息定位一下
        SoftKeyBoardListener.setListener(Chat.this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                setLast();
            }

            @Override
            public void keyBoardHide(int height) {
                setLast();
            }
        });
    }

    @Override
    public void bindView() {
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recylerview);
        backBotton = (ImageView) findViewById(R.id.backbtn);
        detailBtn = (ImageView) findViewById(R.id.detailbtn);
        sendBtn = (ImageView) findViewById(R.id.sendbtn);
        editBox = (EditText) findViewById(R.id.editbox);
        textView = (TextView) findViewById(R.id.text_view);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendbtn:
                String s = editBox.getText().toString();
                if (!s.equals("")) {
                    Msg msg = new Msg(getCurrentTime(), msgList.clientID, s);
                    msgList.msgs.add(msg);
                    msgAdapter.setMsgs(msgList.msgs);
                    setLast();
                    //发送之前先存到内存中
//                    final String url, HashMap<String,Object> hashMap, Context context, final Handler handler , final int code
                    hashMap =new HashMap<>();
//                    String jsonMsg = GsonUtil.toJson(msg);
                    hashMap.put("2",msg);

                    NetWorkUtils.startPost(IP.LOCALHOST + "index", hashMap,context, handler, 1);

                    MsgListList msgListList = IvmApplication.config.getChatMsg("");

                    for (int i = 0; i < msgListList.getMsgLists().size(); i++) {//
                        if (msgListList.getMsgLists().get(i).clientID.equals(msg.reciveID)) {
                            msgListList.getMsgLists().get(i).msgs.add(msg);
                            IvmApplication.config.setChatmsg(msgListList);
                            break;
                        }
                    }
                    editBox.setText("");
                }
                break;
            case R.id.detailbtn:
                ToastUtil.showToast(getApplicationContext(), "用户详情尚未开放");
                break;
            case R.id.backbtn:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case 1:
                if (msg.obj != null) {
//                    String json = (String) msg.obj;
//                    OrderResponse response = (OrderResponse) GsonUtil.toObject(json, OrderResponse.class);
//                    if (response != null && response.result != null && response.result.size() > 0) {
//                        adapter.settDingdanList(response.result);
//                    }
//                    LogUtil.log(TAG, msg.obj.toString());
                    ToastUtil.showToast(context,"发送成功");
                }else{
                    ToastUtil.showToast(context,"发送失败");
                }
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    /**
     * 传递到Contacts去同步
     */
    public class EventInChat {
        public MsgList msgList;

        public EventInChat(MsgList msgList) {
            this.msgList = msgList;
        }
    }

    /**
     * 将聊天消息的最后一条显示出来
     */
    public void setLast() {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getApplicationContext()) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        if (msgList.msgs != null) {
            smoothScroller.setTargetPosition(msgList.msgs.size() + 1);
        }
        linearLayoutManager.startSmoothScroll(smoothScroller);
    }

    /**
     * @return 获取当前时间
     */
    public String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");// HH:mm:ss
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    // Called in Android UI's main thread
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MainActivity.DataChangeEvent event) {
        if (event.b) {
            MsgListList msgListList = IvmApplication.config.getChatMsg("");
            for (int i = 0; i < msgListList.getMsgLists().size(); i++) {
                if (msgListList.getMsgLists().get(i).clientID.equals(msgList.clientID)) {
                    msgList = msgListList.getMsgLists().get(i);
                }
            }
            msgAdapter.setMsgs(msgList.msgs);
            msgAdapter.notifyDataSetChanged();
            setLast();
        }
    }
}
