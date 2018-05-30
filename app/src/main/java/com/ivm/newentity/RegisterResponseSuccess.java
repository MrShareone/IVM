package com.ivm.newentity;

import com.ivm.entity.TKehu;

import java.io.Serializable;

/**
 * Created by MR-SHAREONE on 2018/1/30.
 */

public class RegisterResponseSuccess implements Serializable {

    /**
     * Code : 200
     * Desc : 成功
     * result : {"kHID":10080,"khBonus":0,"khDizhiBiaoqian":"","khImei":"","khIvb":0,"khLastTime":null,"khMima":"ce72acaeeb25c2022e1386d2d1b1a97c","khNote":"{\"key\":\"634763\",\"time\":1517292900175}","khShangciGPS":"","khShenfenzheng":"","khStatus":0,"khTel":"13437105745","khWeixinChengshi":"","khWeixinSex":0,"khWeixinShengfen":"","khWeixinhao":"","khWeixinname":"","khZhifubaoZhanghao":""}
     */

    public String Code;
    public String Desc;
    public TKehu result;
    
    public static class ResultBean {
        /**
         * kHID : 10080
         * khBonus : 0
         * khDizhiBiaoqian : 
         * khImei : 
         * khIvb : 0
         * khLastTime : null
         * khMima : ce72acaeeb25c2022e1386d2d1b1a97c
         * khNote : {"key":"634763","time":1517292900175}
         * khShangciGPS : 
         * khShenfenzheng : 
         * khStatus : 0
         * khTel : 13437105745
         * khWeixinChengshi : 
         * khWeixinSex : 0
         * khWeixinShengfen : 
         * khWeixinhao : 
         * khWeixinname : 
         * khZhifubaoZhanghao : 
         */

        public int kHID;
        public int khBonus;
        public String khDizhiBiaoqian;
        public String khImei;
        public int khIvb;
        public Object khLastTime;
        public String khMima;
        public String khNote;
        public String khShangciGPS;
        public String khShenfenzheng;
        public int khStatus;
        public String khTel;
        public String khWeixinChengshi;
        public int khWeixinSex;
        public String khWeixinShengfen;
        public String khWeixinhao;
        public String khWeixinname;
        public String khZhifubaoZhanghao;
    }
}
