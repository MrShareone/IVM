package com.ivmai.kehu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import com.ivm.entity.TMyDanju;
import com.ivmai.kehu.R;
import com.ivmai.kehu.activity.MyBillActivity;

/**
 * Created by lk on 2017/7/30.
 */

public class  MyBillAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<TMyDanju> tMyDanjuList;

    private LayoutInflater mInflater;

    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView




    public MyBillAdapter(List<TMyDanju> tDanjuList1, Context context) {
        this.mInflater=LayoutInflater.from(context);
        tMyDanjuList=tDanjuList1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //进行判断显示类型，来创建返回不同的View
        if(viewType==TYPE_ITEM){
            View view=mInflater.inflate(R.layout.my_bill_item,parent,false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            ItemViewHolder itemViewHolder=new ItemViewHolder(view);
            return itemViewHolder;
        }else if(viewType==TYPE_FOOTER){
            View foot_view=mInflater.inflate(R.layout.my_order_footview,parent,false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder=new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;

//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_bill_item,parent,false);
//       final MyViewHoler holder=new MyViewHoler(view);
//        return holder;
    }

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        if(holder instanceof ItemViewHolder) {
           TMyDanju tMyDanju=tMyDanjuList.get(position);
            ((ItemViewHolder)holder).texttime.setText(df.format(tMyDanju.getDjJIluTime()));
            ((ItemViewHolder)holder).texttype.setText(tMyDanju.zdleixing());
            ((ItemViewHolder)holder).textamount.setText(tMyDanju.zdjine());

            holder.itemView.setTag(position);
        }else if(holder instanceof FootViewHolder){
            FootViewHolder footViewHolder=(FootViewHolder)holder;
            footViewHolder.foot_view.setText("正在加载...");
            if (MyBillActivity.start >= 0 && getItemCount() < (MyBillActivity.start + 1) * 10) {
                footViewHolder.foot_view.setVisibility(View.GONE);
                footViewHolder.progressBar.setVisibility(View.GONE);
            } else {
                footViewHolder.foot_view.setVisibility(View.VISIBLE);
            }
        }


//        TMyDanju tMyDanju=tMyDanjuList.get(position);
//        holder.texttime.setText(df.format(tMyDanju.getDjJIluTime()));
//        holder.texttype.setText(tMyDanju.zdleixing());
//        holder.textamount.setText(tMyDanju.zdjine());
    }
    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }

    }
    @Override
    public int getItemCount() {
        return tMyDanjuList.size()+1;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private   TextView texttime,texttype,textamount;

        public ItemViewHolder(View view){
            super(view);
            texttime=(TextView)itemView.findViewById(R.id.bill_time);
            texttype=(TextView)itemView.findViewById(R.id.bill_type);
            textamount=(TextView)itemView.findViewById(R.id.bill_amount);
        }
    }
    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends  RecyclerView.ViewHolder{
        private   TextView foot_view;
        private    ProgressBar progressBar;
        public FootViewHolder(View view) {
            super(view);
            progressBar=(ProgressBar)view.findViewById(R.id.my_order_progressBar);
            foot_view=(TextView)view.findViewById(R.id.my_order_foot);
        }
    }
}
