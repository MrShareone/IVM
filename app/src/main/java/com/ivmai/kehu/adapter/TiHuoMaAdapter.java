package com.ivmai.kehu.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.TiHuoMa;

import java.util.List;

/**
 * Created by lk on 2017/8/12.
 */

public class TiHuoMaAdapter extends ArrayAdapter<TiHuoMa> {

    private int resourceid;
    public TiHuoMaAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TiHuoMa> objects) {
        super(context, resource, objects);
        resourceid=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TiHuoMa tiHuoMa=getItem(position);
        View view= LayoutInflater.from(parent.getContext()).inflate(resourceid,parent,false);
        TextView textView=(TextView)view.findViewById(R.id.tihuoma_show);
        textView.setText(tiHuoMa.getTihuoma());
        return view;
    }
}
