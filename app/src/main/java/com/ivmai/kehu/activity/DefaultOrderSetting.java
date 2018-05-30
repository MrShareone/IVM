package com.ivmai.kehu.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.ivmai.kehu.R;

public class DefaultOrderSetting extends AppCompatActivity {
    SharedPreferences morengoumaifangshi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_order_setting);
        morengoumaifangshi = getApplicationContext().getSharedPreferences("morendaigoufangshi", Context.MODE_APPEND);
        final CheckBox checkBox = (CheckBox)findViewById(R.id.morengoumaifangshi);

        int x = morengoumaifangshi.getInt("morendaigoufangshi",1);
        if(x == 1){
            checkBox.setChecked(true);
        }else if(x == 2){
            checkBox.setChecked(false);
        }

        //当这个checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int a = checkBox.isChecked() ? 1 : 2;

                SharedPreferences.Editor editor = morengoumaifangshi.edit();
                editor.putInt("morendaigoufanghi", a); //1为默认公司代购2为普通购
                editor.apply();
            }
        });
    }
}
