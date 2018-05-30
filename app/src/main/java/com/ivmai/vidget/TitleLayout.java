package com.ivmai.vidget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ivmai.kehu.R;

/**
 * Created by lk on 2017/8/3.
 * author shareone
 * 统一样式的顶部状态栏
 */

public class TitleLayout extends LinearLayout implements View.OnClickListener {
    public TextView title_name;
    public ImageView image;
    public BackPressed backPressed;
    public ImageView menu;
    public MenuAction menuAction;


    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title, this);

        image = (ImageView) findViewById(R.id.back);
        title_name = (TextView) findViewById(R.id.title_name);
        menu = (ImageView)findViewById(R.id.menu);

        image.setOnClickListener(this);
        menu.setOnClickListener(this);
    }

    public void setTitle(String s) {
        title_name.setText(s);
    }

    public void setBackPressed(BackPressed backPressed) {
        this.backPressed = backPressed;
    }

    public void setMenuPic(int id){
        menu.setImageResource(id);
    }

    public void setMenuAction(MenuAction menuAction) {
        this.menuAction = menuAction;
    }

    public void setMenuVisibility(Boolean b){
        if(b){
            menu.setVisibility(VISIBLE);
        }else{
            menu.setVisibility(GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                if(backPressed != null){
                    backPressed.backPressed();
                }else{
                    ((Activity) getContext()).onBackPressed();
                }
                break;
            case R.id.menu:
                if(menuAction != null){
                    menuAction.menuClicked();
                }
                break;

            default:
                break;
        }
    }

    interface BackPressed {
        public void backPressed();
    }

    public interface MenuAction{
        public void menuClicked();
    }


}
