package com.ivmai.kehu.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.ivm.entity.TMyVhuiyuanSH;
import com.ivm.newentity.VipListResponse;
import com.ivmai.kehu.R;
import com.ivmai.kehu.activity.HYdetailsActivity;

/**
 * Created by lk on 2017/7/27.
 */

public class HuyuanAdapter extends RecyclerView.Adapter<HuyuanAdapter.MyViewHoler> implements View.OnClickListener{
    public Context mContext;
    private List<VipListResponse.VipMsg> vipMsgList=new ArrayList<>();

    public HuyuanAdapter(Context mContext,List<VipListResponse.VipMsg> vipMsgs) {
        this.mContext = mContext;
        this.vipMsgList=vipMsgs;
    }

    public void setVipMsgList(List<VipListResponse.VipMsg> vipMsgList) {
        if(vipMsgList != null){
            this.vipMsgList = vipMsgList;
            notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        final  View view = LayoutInflater.from(mContext).inflate(R.layout.hy_item, parent, false);
        MyViewHoler holder = new MyViewHoler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHoler holder, int position) {
        VipListResponse.VipMsg vipMsg=vipMsgList.get(position);
        holder.sh_name.setText(vipMsg.shName);
        holder.hyb_yue.setText(Integer.toString(vipMsg.hyShbyue));
        holder.hy_type.setText(Integer.toString(vipMsg.hyJibie));
        holder.itemView.setTag(vipMsgList.get(position));
        holder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        if (vipMsgList != null) {
            return vipMsgList.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onClick(View view) {
                VipListResponse.VipMsg vipMsg = (VipListResponse.VipMsg) view.getTag();
                Intent intent = new Intent(mContext, HYdetailsActivity.class);
                intent.putExtra("vipMsg",vipMsg);
                mContext.startActivity(intent);
    }

    static class MyViewHoler extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView sh_name;
        TextView hyb_yue;
        TextView hy_type;
        public MyViewHoler(View itemView) {
            super(itemView);
            sh_name=(TextView)itemView.findViewById(R.id.sh_name);
            hyb_yue=(TextView)itemView.findViewById(R.id.hyb_yue);
            hy_type=(TextView)itemView.findViewById(R.id.hy_type);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                default:break;
            }
        }
    }

}

