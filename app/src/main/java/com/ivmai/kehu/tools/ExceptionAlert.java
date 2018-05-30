package com.ivmai.kehu.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.ivmai.base.IvmApplication;

/**
 * Created by lk on 2017/8/28.
 */

public class ExceptionAlert {

    public static void cancelalert(Context context){
        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        dialog.setTitle("网络异常：（");
        dialog.setMessage("对不起，由于某些原因导致功能异常，请稍后再试。");
        dialog.setCancelable(true);
        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                IvmApplication.context.startActivity(intent);
                System.exit(0);


            }
        });
        dialog.show();
    }
}
