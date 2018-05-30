package com.ivmai.kehu.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by MR-SHAREONE on 2018/1/21.
 * toast提示工具，添加了开关
 */
public class ToastUtil {

    private static final boolean  ON_OFF = true;   // 开关

    public static void showToast(Context context, String content) {

        if(!ON_OFF)return;


        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();

    }
}