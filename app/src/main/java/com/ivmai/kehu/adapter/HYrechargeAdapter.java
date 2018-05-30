package com.ivmai.kehu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import com.ivmai.kehu.tools.HYrecharge;
import com.ivmai.kehu.activity.HYrechargeActivity;
import com.ivmai.kehu.R;

/**
 * Created by lk on 2017/7/28.
 */

public class HYrechargeAdapter extends RecyclerView.Adapter<HYrechargeAdapter.ViewHolder> {

    private  List<HYrecharge> mhYrechargeList ;
    private Context mContext;
    private MyItemClickListener mItemClickListener;

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private MyItemClickListener mListener;
        TextView textView;

//类ViewHolder构造方法，传入RecyclerView子布局，获取子布局内控件实例
        public ViewHolder(View itemView,MyItemClickListener myItemClickListener) {
            super(itemView);
            this.mListener=myItemClickListener;
            itemView.setOnClickListener(this);
            textView=(TextView)itemView.findViewById(R.id.money);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, getPosition());
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

    public HYrechargeAdapter(HYrechargeActivity hYrechargeActivity, List<HYrecharge> hYrechargeList){
      this.mContext=hYrechargeActivity;
        mhYrechargeList=hYrechargeList;
    }
//创建ViewHolder实例，在其中将hy_recharge_item布局加载进来
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.hy_recharge_item,parent,false);
        final ViewHolder holder=new ViewHolder(view,mItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HYrecharge hYrecharge=mhYrechargeList.get(position);
        holder.textView.setText(hYrecharge.getMoney());
    }


    @Override
    public int getItemCount() {
        return mhYrechargeList.size();
    }
}
