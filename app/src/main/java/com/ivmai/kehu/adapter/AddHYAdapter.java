package com.ivmai.kehu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ivm.entity.TShanghu;
import com.ivmai.kehu.R;
import com.ivmai.kehu.activity.AddHYActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk on 2017/8/18.
 */

public class AddHYAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Context mContext;
    private List<TShanghu> mtShanghuList=new ArrayList<>();
    private MyItemClickListener mItemClickListener;

    private LayoutInflater mInflater;

    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView

//    static class MyViewHoler extends RecyclerView.MyViewHoler implements View.OnClickListener{
//        private MyItemClickListener mListener;
//
//        TextView hy_add_shname;
//
//        public MyViewHoler(View itemView,MyItemClickListener myItemClickListener) {
//            super(itemView);
//            this.mListener=myItemClickListener;
//
//            hy_add_shname=(TextView)itemView.findViewById(R.id.hy_add_shname);
//
//
//            itemView.setOnClickListener(this);
//        }
//        @Override
//        public void onClick(View view) {
//            mListener.onItemClick(view, getLayoutPosition());
//        }
//    }

    //    /**
//     * 创建一个回调接口
//     */
    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }
    //
//    /**
//     * 在activity里面adapter就是调用的这个方法,将点击事件监听传递过来,并赋值给全局的监听
//     *
//     * @param myItemClickListener
//     */
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }

    public AddHYAdapter(Context context,List<TShanghu> tShanghuList) {
        this.mInflater=LayoutInflater.from(context);
        this.mtShanghuList=tShanghuList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }

        //进行判断显示类型，来创建返回不同的View
        if(viewType==TYPE_ITEM){
            View view=mInflater.inflate(R.layout.add_huiyan_item,parent,false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            ItemViewHolder itemViewHolder=new ItemViewHolder(view,mItemClickListener);
            return itemViewHolder;
        }else if(viewType==TYPE_FOOTER){
            View foot_view=mInflater.inflate(R.layout.my_order_footview,parent,false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder=new FootViewHolder(foot_view);
            return footViewHolder;
        }
//        final  View view = LayoutInflater.from(context).inflate(R.layout.add_huiyan_item, parent, false);
//        MyViewHoler holder = new MyViewHoler(view,mItemClickListener);
//        return holder;
        return  null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ItemViewHolder) {
           TShanghu tShanghu=mtShanghuList.get(position);
            ((ItemViewHolder)holder).hy_add_shname.setText(tShanghu.getShName());
//            holder.itemView.setTag(position);
        }else if(holder instanceof FootViewHolder){
            FootViewHolder footViewHolder=(FootViewHolder)holder;
            footViewHolder.foot_view.setText("正在加载...");
            if (AddHYActivity.start >= 0 && getItemCount() < (AddHYActivity.start + 1) * 20) {
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
        return mtShanghuList.size()+1;
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyItemClickListener mListener;

        private   TextView hy_add_shname;

        public ItemViewHolder(View view,MyItemClickListener myItemClickListener){
            super(view);

            this.mListener=myItemClickListener;

            hy_add_shname=(TextView)itemView.findViewById(R.id.hy_add_shname);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v){
            mListener.onItemClick(v, getLayoutPosition());
        }
    }
    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends  RecyclerView.ViewHolder{
        private   TextView foot_view;
        private ProgressBar progressBar;
        public FootViewHolder(View view) {
            super(view);
            progressBar=(ProgressBar)view.findViewById(R.id.my_order_progressBar);
            foot_view=(TextView)view.findViewById(R.id.my_order_foot);
        }
    }

}
