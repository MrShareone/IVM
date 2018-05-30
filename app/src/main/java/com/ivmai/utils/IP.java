package com.ivmai.utils;

/**
 * Created by MR-SHAREONE on 2018/1/21.
 * 存放管理ip
 */

public class IP {
    public static final int YUFA = 0;    //预发环境
    public static final int ONLINE = YUFA + 1;   //生产环境

    private int environment = ONLINE;    //在这里更改ip地址


    public static String SERVERURL = "https://ivmai.cn/";
    public static String SERVERURI = "ivm://ivmai.cn/";
    public static String IMG_SERVERURL = "https://ivmai.cn/";
    public static String LOCALHOST = "http://192.168.137.1:8080/";


    public static void initIP(int type) {
        switch (type) {
            case YUFA:
                SERVERURL = "http://192.168.1.137:8080/ivm/";
                SERVERURI = "ivm://192.168.1.137:8080/ivm/";
                IMG_SERVERURL = "http://192.168.1.137:8080/";
                LOCALHOST = "http://192.168.137.1:8080/";
                break;

            case ONLINE:
                SERVERURL = "https://ivmai.cn/";
                SERVERURI = "ivm://ivmai.cn/";
                IMG_SERVERURL = "https://ivmai.cn/";
                LOCALHOST = "http://192.168.137.1:8080/";
                break;
            default:
                break;
        }
    }

}
