package com.ivmai.kehu.chat.activity;


import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.ivmai.base.BaseActvity;
import com.ivmai.base.IvmApplication;

import com.ivmai.bdpush.ChatMsgEvet;
import com.ivmai.kehu.R;
import com.ivmai.kehu.activity.MainActivity;
import com.ivmai.kehu.chat.adapter.ContactsAdapter;

import com.ivmai.kehu.chat.bean.Msg;
import com.ivmai.kehu.chat.bean.MsgList;
import com.ivmai.kehu.chat.bean.MsgListList;
import com.ivmai.utils.DisplayUtil;
import com.ivmai.utils.LogUtil;
import com.ivmai.vidget.RecycleViewDivider;
import com.ivmai.vidget.TitleLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

/**
*Contacts
*这是联系人列表
*MR-SHAREONE
*2018/5/19
*20:45
**/
public class Contacts extends BaseActvity implements View.OnClickListener {
    TextView title;

    RecyclerView recyclerView;

    ContactsAdapter contactsAdapter;
    MsgListList msgListList;
    TitleLayout titleLayout;

    EditText search_text;
    ImageView search_icon_img;

    @Override
    public int getViewId() {
        return R.layout.activity_contacts;
    }

    @Override
    public void prepareWork() {
        title.setText("联系人");
        msgListList = IvmApplication.config.getChatMsg("");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        contactsAdapter = new ContactsAdapter(getApplicationContext(), msgListList);
        recyclerView.setAdapter(contactsAdapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(context, RecyclerView.HORIZONTAL, DisplayUtil.dip2px(context, 10), getResources().getColor(R.color.light_primary_color)));
//        titleLayout.setMenuAction(new TitleLayout.MenuAction() {
//            @Override
//            public void menuClicked() {
//
//            }
//        });
    }

    @Override
    public void bindView() {
        title = (TextView) findViewById(R.id.title_name);
        recyclerView = (RecyclerView) findViewById(R.id.contactsrecyclerview);
        titleLayout = (TitleLayout) findViewById(R.id.titlebar);
        search_text = (EditText) findViewById(R.id.search_edit_input);
        search_icon_img = (ImageView) findViewById(R.id.search_icon_img);
        search_icon_img.setOnClickListener(this);
//        titleLayout.setMenuVisibility(true);
//        titleLayout.setMenuPic(R.drawable.add);
        EventBus.getDefault().register(this);  //eventbus必须在界面初始化完成后再注册，不然响应不了
    }

    // Called in Android UI's main thread
    //聊天页面返回的时候传送消息回来，在这个地方更改，这么做是应为，intent传过去的值是复制的，而不是引用
    //我其实可以传个id过去，在那里访问内存，获取引用，那么修改的就是同一个地方了
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(Chat.EventInChat event){
        MsgList msgList = event.msgList;
        for (int i = 0; i < msgListList.getMsgLists().size(); i++) {
            msgListList = IvmApplication.config.getChatMsg("");
            if (msgListList.getMsgLists().get(i).clientID.equals(msgList.clientID)) {
                msgListList.getMsgLists().remove(i);
                msgListList.getMsgLists().add(0, msgList);
//               msgListList.getMsgLists().add(msgList);
                contactsAdapter.setMsgListList(msgListList);
            }
        }
    }

    // Called in Android UI's main thread
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MainActivity.DataChangeEvent event) {
        if (event.b) {
            msgListList = IvmApplication.config.getChatMsg("");
            contactsAdapter.setMsgListList(msgListList);
            contactsAdapter.notifyDataSetChanged();
            LogUtil.log("shuaxindangqianyemian", "contacts");
        } else {
            ;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EventBus.getDefault().post(new ChatMsgEvet(false));
        IvmApplication.config.setChatmsg(msgListList);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_icon_img:
                String text = search_text.getText().toString();
                if(!text.equals("")){
                    MsgListList  msgListList = IvmApplication.config.getChatMsg("");
                    if(msgListList != null){

                        for(int i=0;i<msgListList.getMsgLists().size();i++){//如果，内存中已经有这条记录，那么我们就取出这条记录传到聊天界面
                            if(msgListList.getMsgLists().get(i).clientID.equals(text)){
                                Intent intent = new Intent(context, Chat.class);
                                intent.putExtra("fromeContacts",msgListList.getMsgLists().get(i));
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                                startActivity(intent);
                                return;
                            }
                        }
                        ArrayList<Msg> msgs = new ArrayList<>();//如果没有记录，我们就新建一条记录传到chat
                        MsgList msgList = new MsgList(IvmApplication.kehu.khTel,text,msgs,false);
                        msgListList.getMsgLists().add(msgList);
                        IvmApplication.config.setChatmsg(msgListList);

                        Intent intent = new Intent(context, Chat.class);
                        intent.putExtra("fromeContacts",msgList);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                        startActivity(intent);
                    }else{
                        //如果还没有聊天内存，我们新建并存储
                        msgListList = new MsgListList();
                        IvmApplication.config.setChatmsg(msgListList);
                    }
                }
                break;
            default:
                break;
        }
    }
}
