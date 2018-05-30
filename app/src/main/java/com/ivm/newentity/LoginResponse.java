package com.ivm.newentity;

import java.io.Serializable;

/**
 * Created by MR-SHAREONE on 2018/1/21.
 */

public class LoginResponse implements Serializable{
    /**
     * Code : 200
     * Desc : 成功
     * result : {"kHID":10009,"khBonus":0,"khDizhiBiaoqian":"","khImei":"","khIvb":114.33,"khLastTime":null,"khMima":"ebf51553322cf7bd885b046f3f839de7","khNote":"{\"key\":\"334934\",\"time\":1516200280456}","khShangciGPS":"POINT(30.492192 114.384887)","khShenfenzheng":"","khStatus":10,"khTel":"13437105740","khWeixinChengshi":"","khWeixinSex":2,"khWeixinShengfen":"","khWeixinhao":"","khWeixinname":"","khZhifubaoZhanghao":""}
     */

    public String Code;
    public String Desc;
    public Object result;
}
