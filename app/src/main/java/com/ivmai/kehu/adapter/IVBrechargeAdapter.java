package com.ivmai.kehu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivmai.kehu.tools.IVBrecharge;
import com.ivmai.kehu.activity.IVBrechargeActivity;
import com.ivmai.kehu.R;
import java.util.List;

/**
 * Created by lk on 2017/7/29.
 */

public class IVBrechargeAdapter extends RecyclerView.Adapter<IVBrechargeAdapter.ViewHolder> {

    private List<IVBrecharge> mivBrechargeList;
    private Context mContext;
    private MyItemClickListener mItemClickListener;

     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private MyItemClickListener mListener;
         TextView textView;

        public ViewHolder(View itemView,MyItemClickListener myItemClickListener) {
            super(itemView);
            this.mListener=myItemClickListener;
            itemView.setOnClickListener(this);
           textView=(TextView)itemView.findViewById(R.id.ivb);
        }

        @Override
        public void onClick(View v){
            if (mListener != null) {
                mListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

    /**
     * 创建一个回调接口
     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }
    /**
     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
     *
     * @param myItemClickListener
     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }

    public IVBrechargeAdapter(IVBrechargeActivity ivBrechargeActivity,List<IVBrecharge> ivBrechargeList) {
        this.mContext=ivBrechargeActivity;
        this.mivBrechargeList=ivBrechargeList;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ivb_recharge_item,parent,false);
        final  ViewHolder holder=new ViewHolder(view,mItemClickListener);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        IVBrecharge ivBrecharge=mivBrechargeList.get(position);
        holder.textView.setText(ivBrecharge.getIvb());
    }
    @Override
    public int getItemCount() {
        return mivBrechargeList.size();
    }
}
