package com.ivmai.kehu.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.Biaoqian;
import com.ivmai.kehu.tools.FlowRadioGroup;

import java.util.List;

/**
 * Created by MR-SHAREONE on 2017/8/16.
 */

public class BiaoqianAdapter extends RecyclerView.Adapter<BiaoqianAdapter.ViewHolder> {
    private List<Biaoqian> biaoqianList;
    Context contex;
    MyItemClickListener mItemClickListener;


    public BiaoqianAdapter(List<Biaoqian> biaoqianList,Context context){
        this.biaoqianList = biaoqianList;
        this.contex = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        if(contex!=null){
            contex = parent.getContext();
        }
        return  holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Biaoqian biaoqian = biaoqianList.get(position);
        String[] biaoqians = biaoqian.getStrings();
        if(biaoqians.length == 1 && biaoqians[0] == ""){
            ;
        }else {
            for (int i = 0; i < biaoqians.length; i++) {
                RadioButton tempbutton = (RadioButton) LayoutInflater.from(contex).inflate(R.layout.radio_button, null);
                Log.d("there", biaoqians[i]);
                tempbutton.setText(biaoqians[i]);
                holder.flowRadioGroup.addView(tempbutton);

                if (i == 0) {
                    holder.flowRadioGroup.check(tempbutton.getId());
                }
            }
        }

        if(mItemClickListener != null) {
            holder.flowRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                    mItemClickListener.onCheckedChanged(radioGroup,i,position);
                }
            });
        }
    }
//自定义一个接口
    public interface MyItemClickListener extends RadioGroup.OnCheckedChangeListener {
    @Override
    void onCheckedChanged(RadioGroup radioGroup, @IdRes int i);

        void onCheckedChanged(RadioGroup radioGroup, @IdRes int i,int t);
    }

//adapter的函数，用来设置adaapter的接口
    public void setItemClickListener(MyItemClickListener myItemClickListener) {
        this.mItemClickListener = myItemClickListener;
    }

    @Override
    public int getItemCount() {
        return  biaoqianList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        FlowRadioGroup flowRadioGroup;
        public ViewHolder(View view ){//viewholder的构造器
            super(view);
            flowRadioGroup = (FlowRadioGroup)view.findViewById(R.id.myradiogrop);
        }

    }
}

