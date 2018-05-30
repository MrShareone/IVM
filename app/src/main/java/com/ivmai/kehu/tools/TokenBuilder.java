package com.ivmai.kehu.tools;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

import com.ivmai.base.IvmApplication;

/**
 * Created by MR-SHAREONE on 2017/8/28.
 * 构建token
 * * timestamp获取方式：Timestamp d = new Timestamp(System.currentTimeMillis());
 *id： 客户id
 * token结构： 时间字符串+商户or客户识别码（2,1）+用户id+（时间+商户or客户识别码+用户id+key）的md5码
 */

public class TokenBuilder {
    public static String buildToken() {
        Long id;
        if (IvmApplication.kehu != null) {
            id = IvmApplication.kehu.kHID;
        } else {
            id = 0L;
        }

        Timestamp now = new Timestamp(System.currentTimeMillis());
        String key;
        if (IvmApplication.kehuNoteBean != null) {
            key = IvmApplication.kehuNoteBean.key;
        } else {
            key = "";
        }
        int flag = 1;//1表示客户，2表示商户
        String a = time2string(now);
        String inStr = a + flag + id + key;
        String md5 = "";
        try {
            md5 = MD5.md5Encode(inStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a + flag + ToBase64(id) + md5;
    }

    public static String ToBase64(long a) {
        BASE64Encoder encoder = new BASE64Encoder();

        byte[] bb = int2Bytes((int) a);
        String str = encoder.encode(bb);
        str = str.replace("+", "@");
        str = str.replace("/", "#");
        return str;
    }


    public static String time2string(Timestamp now) {
        String format = "yyyy-MM-ddHH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINA);
        String time = sdf.format(now);
        time = time.replace("-", "");
        time = time.replace(":", "");
        return time;
    }


    public static byte[] int2Bytes(int num) {
        byte[] byteNum = new byte[4];
        for (int ix = 0; ix < 4; ++ix) {
            int offset = 32 - (ix + 1) * 8;
            byteNum[ix] = (byte) ((num >> offset) & 0xff);
        }
        return byteNum;
    }

}
