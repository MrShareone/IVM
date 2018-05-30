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

import com.ivm.entity.TMyDingdan;
import com.ivmai.kehu.R;
import com.ivmai.kehu.activity.MyOrderActivity;

/**
 * Created by lk on 2017/8/3.
 */

public class HistoryOrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
   private List<TMyDingdan> mTDingdanList;


    private LayoutInflater mInflater;

    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView

    public HistoryOrderAdapter(List<TMyDingdan> TDingdanList,Context context) {
        this.mInflater=LayoutInflater.from(context);
        mTDingdanList=TDingdanList;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if(viewType==TYPE_ITEM){
            View view=mInflater.inflate(R.layout.my_order_item,parent,false);
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


//        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.my_order_item,parent,false);
//       final MyViewHoler holder=new MyViewHoler(view);
//        return holder;
    }

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ItemViewHolder) {
            TMyDingdan tDingdan=mTDingdanList.get(position);
            ((ItemViewHolder)holder).time.setText(df.format(tDingdan.ddDingdanTime));
            ((ItemViewHolder)holder).dpname.setText(tDingdan.shName);
            ((ItemViewHolder)holder).spname.setText(tDingdan.spName);
            ((ItemViewHolder)holder).splabel.setText(tDingdan.ddKexuanBiaoqianZhi);
            ((ItemViewHolder)holder).spprice.setText(tDingdan.ddvalue.toString());
            ((ItemViewHolder)holder).take_way.setText(tDingdan.ddTihuoDidian);
            ((ItemViewHolder)holder).state.setText(tDingdan.ddstate());
            ((ItemViewHolder)holder).dingdannum.setText(tDingdan.ddSPNum.toString());
            holder.itemView.setTag(position);
        }else if(holder instanceof FootViewHolder){
            FootViewHolder footViewHolder=(FootViewHolder)holder;
            footViewHolder.foot_view.setText("正在加载...");
            if (MyOrderActivity.start >= 0 && getItemCount() < (MyOrderActivity.start + 1) * 10) {
                footViewHolder.foot_view.setVisibility(View.GONE);
                footViewHolder.progressBar.setVisibility(View.GONE);
            } else {
                footViewHolder.foot_view.setVisibility(View.VISIBLE);
            }
        }
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
        return mTDingdanList.size()+1;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ItemViewHolder extends RecyclerView.ViewHolder {
       private   TextView time,dpname,spname,splabel,spprice,take_way,state,dingdannum;

        public ItemViewHolder(View view){
            super(view);
            time=(TextView)itemView.findViewById(R.id.my_order_time);
            dpname=(TextView)itemView.findViewById(R.id.my_order_dm);
            spname=(TextView)itemView.findViewById(R.id.my_order_spm);
           splabel=(TextView)itemView.findViewById(R.id.my_sp_label);
            spprice=(TextView)itemView.findViewById(R.id.my_order_price);
            take_way=(TextView)itemView.findViewById(R.id.sp_take_way);
           state=(TextView)itemView.findViewById(R.id.my_order_state);
            dingdannum = (TextView)itemView.findViewById(R.id.dingdannum);
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
