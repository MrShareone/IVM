package com.ivm.entity;

import java.io.Serializable;

/**
 * Created by lk on 2017/8/9.
 */

public class TMyVhuiyuanSH extends VhuiyuanSH{
    public String strHyjibie(){
         int a=this.getHyJibie();
        if (a==1)
            return "普通会员";
        if (a==2)
            return "银卡会员";
        if (a==3)
            return "金卡会员";
        if (a==4)
            return "钻石会员";
        return "";
    }

    public String strHyzhekou(){
        int a=this.getHyJibie();
        if (a==1)
            return "9";
        if (a==2)
            return "8.5";
        if (a==3)
            return "8";
        if (a==4)
            return "7.5";
        return "";
    }
}
