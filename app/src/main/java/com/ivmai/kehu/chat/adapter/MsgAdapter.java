package com.ivmai.kehu.chat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivmai.base.IvmApplication;
import com.ivmai.kehu.R;
import com.ivmai.kehu.chat.bean.Msg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR-SHAREONE on 2018/5/14.
 */

public class MsgAdapter extends RecyclerView.Adapter {
    public Context context;
    public List<Msg> msgs = new ArrayList<>();


    private static final int LEFTMSGBOX = 1;
    private static final int RIGHTMSGBOX = 2;

    public MsgAdapter(Context context, List<Msg> list) {
        this.context = context;
        this.msgs = list;
    }

    public void setMsgs(List<Msg> msgs) {
        if (msgs != null) {
            this.msgs = msgs;
            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        if(viewType == LEFTMSGBOX){
            View view = LayoutInflater.from(context).inflate(R.layout.msgleftitem, parent, false);
            return new MyLeftMsgHolder(view);
        }else{
            View view = LayoutInflater.from(context).inflate(R.layout.msgrightiitem, parent, false);
            return new MyRightHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Msg msg = msgs.get(position);

        if (holder instanceof MyLeftMsgHolder){
            MyLeftMsgHolder mholder = (MyLeftMsgHolder) holder;
//            mholder.headPic.setImageResource();
            mholder.msg.setText(msg.content);
            mholder.time.setText(msg.time);
        }else{
            MyRightHolder mholder = (MyRightHolder) holder;
//            mholder.headPic.setImageResource();
            mholder.msg.setText(msg.content);
            mholder.time.setText(msg.time);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Msg temMsg = msgs.get(position);
        //如果发送方是自己，那么就显示右方
        if (temMsg.sendID.equals(IvmApplication.kehu.khTel)){
            return RIGHTMSGBOX;
        } else {
            return LEFTMSGBOX;
        }

    }

    @Override
    public int getItemCount() {
        if(msgs == null){
            return 0;
        }else{
            return msgs.size();
        }

    }

    private class MyLeftMsgHolder extends RecyclerView.ViewHolder {

        ImageView headPic;  //头像
        TextView msg;  //消息
        TextView time; //时间

        public MyLeftMsgHolder(View itemView) {
            super(itemView);
            headPic = itemView.findViewById(R.id.headpic);
            msg = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);
        }

    }



    private class MyRightHolder extends RecyclerView.ViewHolder {

        ImageView headPic;  //头像
        TextView msg;  //消息
        TextView time; //时间

        public MyRightHolder(View itemView) {
            super(itemView);
            headPic = itemView.findViewById(R.id.headpic);
            msg = itemView.findViewById(R.id.content);
            time = itemView.findViewById(R.id.time);

        }
    }

}
