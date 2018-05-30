package com.ivmai.kehu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ivmai.kehu.R;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by MR-SHAREONE on 2018/1/31.
 */

public class TagAdpter extends RecyclerView.Adapter <TagAdpter.TagHolder>{
    List<String> tags;
    Context context;

    public TagAdpter(Context context , List<String> tags){
        this.context = context;
        if(tags != null && tags.size() > 0){
            this.tags = tags;
        }
    }

    public void setTags(List<String> tags) {
        if(tags != null){
            this.tags = tags;
            notifyDataSetChanged();
        }
    }

    @Override
    public TagAdpter.TagHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.tag_layout,null);
        TagHolder tagHolder = new TagHolder(view);
        return tagHolder;
    }

    @Override
    public void onBindViewHolder(TagHolder holder, int position) {
        String s = tags.get(position);
        if(!TextUtils.isEmpty(s)){
            holder.tag.setText(s);
        }
    }

    @Override
    public int getItemCount() {
        if(tags == null)
            return 0;
        else
            return tags.size();
    }

    public class TagHolder extends RecyclerView.ViewHolder{

        public TextView tag;

        public TagHolder(View itemView) {
            super(itemView);
            tag = (TextView)itemView.findViewById(R.id.tag);
        }
    }
}
