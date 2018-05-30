package com.ivmai.kehu.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivmai.kehu.R;
import com.ivmai.kehu.chat.activity.Chat;
import com.ivmai.kehu.chat.bean.MsgList;
import com.ivmai.kehu.chat.bean.MsgListList;
import com.ivmai.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by MR-SHAREONE on 2018/5/15.
 */

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> implements View.OnClickListener{
    Context context;
//    ArrayList<MsgList> msgListArrayList = new ArrayList<>();  //这个lis，每个单元就是两个人的聊天记录
    MsgListList msgListList;


    public ContactsAdapter(Context context,MsgListList msgListList) {
        this.context = context;
        if(msgListList != null){
            this.msgListList = msgListList;
        }else{
            this.msgListList = new MsgListList();
        }

    }

    public void setMsgListList(MsgListList msgListList) {
        if(msgListList != null){
            this.msgListList = msgListList;
            notifyDataSetChanged();
        }

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contactitem, parent, false);
        return new ContactsAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        holder.itemView.setOnClickListener(this);
        holder.itemView.setTag(position);

        MsgList msgList = msgListList.getMsgLists().get(position);
        holder.textView.setText(msgList.clientID);    //这里只显示对方的id
        if (msgList.newMessage){
            holder.dot.setVisibility(View.VISIBLE);
        }else{
            holder.dot.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view){
        int i = (int)view.getTag();
        MsgList msgList = msgListList.getMsgLists().get(i);
        LogUtil.log("msglist",msgList.toString());
        if(msgList != null){
            msgList.newMessage = false;
            notifyDataSetChanged();
            Intent intent = new Intent(context, Chat.class);
            intent.putExtra("fromeContacts",msgList);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
            context.startActivity(intent);
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView  textView;   //显示姓名或者id
        View dot;  //显示消息的原点

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.text);
            dot = itemView.findViewById(R.id.dot);
        }
    }

    @Override
    public int getItemCount() {
        if(msgListList.getMsgLists() != null){
            return msgListList.getMsgLists().size();
        }else{
            return 0;
        }
    }
}
