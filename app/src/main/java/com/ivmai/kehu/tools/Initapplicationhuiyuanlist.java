package com.ivmai.kehu.tools;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.fasterxml.jackson.databind.JsonNode;
import com.ivm.entity.VhuiyuanSH;
import com.ivmai.base.IvmApplication;
import com.ivmai.utils.IP;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by MR-SHAREONE on 2017/8/18.
 */

public class Initapplicationhuiyuanlist {
    public static List<VhuiyuanSH> huiyuanList=new ArrayList<>();

    public static  Handler mHand = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2112:
                    Log.d("inithuiyuan","完成");
                    break;
                default:
                    break;
            }
        }
    };
    public void   initHuyuanList() {//初始化会员list
        new Thread(new Runnable() {
            @Override
            public void run() {
                DoGetHuiyuanList();
                Message msg = new Message();
                msg.what = 2112;
                mHand.sendMessage(msg);
            }
        }).start();//

    }
    public void  DoGetHuiyuanList() {
        try {
            OkhttpUtils.getResult(IP.SERVERURL+"vk/KHhy/"+ IvmApplication.kehu.kHID);
            JsonNode jn = OkhttpUtils.getJsonnode();
            if (jn.isArray()) {
                Iterator<JsonNode> iterator = jn.elements();
                PojoMapper pj = new PojoMapper();
                while (iterator.hasNext()) {
                    JsonNode nd = iterator.next();
                    VhuiyuanSH vhuiyuanSH = new VhuiyuanSH();
                    vhuiyuanSH = (VhuiyuanSH) pj.fromJson(nd.toString(), VhuiyuanSH.class);
                    huiyuanList.add(vhuiyuanSH);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
