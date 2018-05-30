package com.ivmai.utils;

import android.util.Log;

/**
 * Created by MR-SHAREONE on 2018/1/21.
 * 设置log的开关，方便在发版和测试时log的控制
 */

public class LogUtil {
    private static final boolean ON_OFF = true;

    public static void log(String tag, String s) {
        if (!ON_OFF) return;

        tag = "LogUtil" + tag;
        Log.e(tag, s);

    }
}
