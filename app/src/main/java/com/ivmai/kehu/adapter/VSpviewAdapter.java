package com.ivmai.kehu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.ivm.entity.VSpview;
import com.ivm.newentity.ProductsResponseSuccess;
import com.ivmai.kehu.R;

import com.ivmai.kehu.fragment.SearchOrderFragment;
import com.ivmai.utils.IP;

/**
 * Created by lk on 2017/7/27.
 */

public class VSpviewAdapter extends RecyclerView.Adapter {

    private List<ProductsResponseSuccess.ResultBean> mVSpviewList = new ArrayList<ProductsResponseSuccess.ResultBean>();

    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView

    public Context context;

    public VSpviewAdapter(List<ProductsResponseSuccess.ResultBean> vSpviews, Context context) {
        if (vSpviews != null && vSpviews.size() > 0) {
            mVSpviewList = vSpviews;
        }
        this.context = context;
    }

    public void setmVSpviewList(List<ProductsResponseSuccess.ResultBean> mVSpviewList) {
        this.mVSpviewList = mVSpviewList;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = View.inflate(context, R.layout.search_item, null);
        ItemViewHolder viewHolder = new ItemViewHolder(view);
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {

            ProductsResponseSuccess.ResultBean vSpview = mVSpviewList.get(position);
            ((ItemViewHolder) holder).storeName.setText(vSpview.shName);
            ((ItemViewHolder) holder).productName.setText(vSpview.spName);
            Glide.with(context).load(IP.SERVERURL+vSpview.spTupian).centerCrop().into(((ItemViewHolder) holder).productPic);
            ((ItemViewHolder) holder).productDescription.setText(vSpview.spJianjie);

        }
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        if (mVSpviewList == null) {
            return 0;
        } else {
            return mVSpviewList.size();
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView storeName;
        private TextView productName;
        private TextView productDescription;
        private ImageView productPic;

        public ItemViewHolder(View view) {
            super(view);

            storeName = (TextView) view.findViewById(R.id.dp_name);
            productName = (TextView) view.findViewById(R.id.sp_name);
            productDescription = (TextView) view.findViewById(R.id.shangpinmiaoshu);
            productPic = (ImageView) view.findViewById(R.id.sp_img);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            /*处理点击事件*/
        }
    }

    /**
     * 底部FootView布局
     */
    public static class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView foot_view;
        private ProgressBar progressBar;

        public FootViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.my_order_progressBar);
            foot_view = (TextView) view.findViewById(R.id.my_order_foot);
        }
    }
}
