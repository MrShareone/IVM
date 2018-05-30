package com.ivmai.kehu.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ivmai.kehu.R;
import com.ivmai.kehu.tools.PersonInfor;

import java.util.List;

/**
 * Created by lk on 2017/8/7.
 */

public class PersonInforAdapter extends ArrayAdapter<PersonInfor> {

    private int resourceid;
    public PersonInforAdapter( Context context, int resource, List<PersonInfor> objects) {
        super(context, resource, objects);
        resourceid=resource;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        PersonInfor personInfor=getItem(position);
        View view= LayoutInflater.from(parent.getContext()).inflate(resourceid,parent,false);
        TextView textView=(TextView)view.findViewById(R.id.person_list_name);
        textView.setText(personInfor.getName());
        return view;
    }
}
