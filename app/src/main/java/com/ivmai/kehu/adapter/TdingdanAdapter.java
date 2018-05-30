package com.ivmai.kehu.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.ivm.newentity.OrderResponse;
import com.ivmai.bdpush.CommonEvent;
import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.ToastUtil;
import com.ivmai.utils.IP;

import org.greenrobot.eventbus.EventBus;

public class TdingdanAdapter extends RecyclerView.Adapter<TdingdanAdapter.MyViewHolder> implements View.OnClickListener {
    public Context context;
    private List<OrderResponse.DingDan> tDingdanList;

    //构造函数
    public TdingdanAdapter(Context context, List<OrderResponse.DingDan> dingDans) {
        this.context = context;
        if (dingDans != null) {
            tDingdanList = dingDans;
        }
    }

    /**
     * @param dingDans
     */
    public void settDingdanList(List<OrderResponse.DingDan> dingDans) {
        if (dingDans != null) {
            this.tDingdanList = dingDans;
            notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        OrderResponse.DingDan dingDan = tDingdanList.get(position);
        holder.itemView.setTag(dingDan);
        holder.itemView.setOnClickListener(this);
        Glide.with(context).load(IP.SERVERURL + dingDan.spTupian).centerCrop().into(holder.productPic);
        holder.ddId.setText(dingDan.dDID + "");
        holder.ddState.setText(dingDan.getStringState() + "");
        holder.realPay.setText(dingDan.getDdValue());
        holder.pickUpMode.setText(dingDan.ddTihuoDidian + "");
        holder.productNum.setText(dingDan.ddSPNum + "份");
        holder.productName.setText(dingDan.spName + "");
        TagAdpter tagAdpter = new TagAdpter(context, dingDan.getTags());
        holder.recyclerView.setAdapter(tagAdpter);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public int getItemCount() {
        if (tDingdanList != null) {
            return tDingdanList.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onClick(View view) {
        OrderResponse.DingDan dingDan = (OrderResponse.DingDan) view.getTag();
        EventBus.getDefault().post(new CommonEvent(100, dingDan));
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerView recyclerView;  //标签，包括价格标签和非价格标签
        TextView ddId, productName, productNum, pickUpMode, realPay, ddState;   //订单id，商品名称，商品数量，提货方式，实际付款,订单状态
        ImageView productPic, callIcon;  //商品图片，电话标志

        public MyViewHolder(View view) {
            super(view);
            recyclerView = (RecyclerView) view.findViewById(R.id.tabs);
            ddId = (TextView) view.findViewById(R.id.ddid);
            productName = (TextView) view.findViewById(R.id.products_name);
            productNum = (TextView) view.findViewById(R.id.products_num);
            pickUpMode = (TextView) view.findViewById(R.id.pickup_mode);
            realPay = (TextView) view.findViewById(R.id.real_pay);
            ddState = (TextView) view.findViewById(R.id.dd_state);
            productPic = (ImageView) view.findViewById(R.id.product_pic);
            productPic.setOnClickListener(this);
            callIcon = (ImageView) view.findViewById(R.id.call_icon);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.product_pic:
                    ToastUtil.showToast(context,"图片");
                    break;
                default:
                    break;
            }
        }
    }
}