package com.ivm.entity.alipay;

/**
 * Created by MR-SHAREONE on 2017/8/12.
 */

public class AlipayResponseMsg {
    public String orderInfo;
    public Long dJID;
    public String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getorderInfo() {
        return orderInfo;
    }

    public void setorderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Long getdJID() {
        return dJID;
    }

    public void setdJID(Long dJID) {
        this.dJID = dJID;
    }
}
